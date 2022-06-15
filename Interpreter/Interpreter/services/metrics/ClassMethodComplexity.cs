using System;
using System.Collections;
using System.Collections.Generic;
using Interpreter.Models.metrics;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.services.metrics
{
    public class ClassMethodComplexity
    {
        public static void CalculateClassMatrics(CompositeTypeSpecifier classeElement, MetricsAditionalData filler,
            ClassOrHeaderWithPath classOrHeaderWithPath, NameSpace nameSpace)
        {
            foreach (var declaration in classeElement.declarations)
            {
                switch (declaration)
                {
                    case FunctionDefinition definition:
                    {
                        CalculateClassMethodAndComplexityFunctionDefinition(definition, filler, classOrHeaderWithPath);
                        ExternMetrics.CheckIfOverridesMethod(filler, definition, classOrHeaderWithPath);
                        break;
                    }
                    case SimpleDeclaration simpleDeclaration when simpleDeclaration.declarators[0] is Declarator:
                        CheckFieldType(simpleDeclaration, filler); // int x; Class y;
                        break;
                    case SimpleDeclaration simpleDeclaration
                        when simpleDeclaration.declarators[0] is not FunctionDeclarator:
                        continue;
                    case SimpleDeclaration simpleDeclaration:
                    {
                        CalculateClassMethodAndComplexitySimpleDeclaration(simpleDeclaration, classOrHeaderWithPath,
                            filler, classeElement, nameSpace);
                        CheckModifier(simpleDeclaration, filler);
                        break;
                    }
                }
            }
        }

        public static void CheckModifier(SimpleDeclaration declaration, MetricsAditionalData filler)
        {
            foreach (var declarator in declaration.declarators)
            {
                if (declarator is FunctionDeclarator)
                {
                    if ((declarator as FunctionDeclarator).modifier.Equals("protected:"))
                    {
                        filler.numberOfProtectedMethodsFields++;
                    }

                    if (declaration.declSpecifier is SimpleDeclSpecifier)
                    {
                        if (((SimpleDeclSpecifier) declaration.declSpecifier).isVirtual)
                        {
                            filler.numberOfAbstractMethods++;
                        }
                    }
                }
                else if (declarator is Declarator)
                {
                    switch ((declarator as Declarator).modifier)
                    {
                        case "public":
                            filler.numberOfPublicFields++;
                            break;
                        case "protected":
                            filler.numberOfProtectedMethodsFields++;
                            break;
                    }
                }
            }
        }

        private static void CheckFieldType(SimpleDeclaration declaration, MetricsAditionalData filler)
        {
            foreach (var declarator in declaration.declarators)
            {
                if (((Declarator) declarator).modifier.Equals("public") ||
                    ((Declarator) declarator).modifier.Equals("public:"))
                {
                    filler.numberOfPublicFields++;
                }
                else if (((Declarator) declarator).modifier.Equals("protected") ||
                         ((Declarator) declarator).modifier.Equals("protected:"))
                {
                    filler.numberOfProtectedMethodsFields++;
                }
            }
        }

        public static void CalculateClassMethodAndComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsInFile filler)
        {
            foreach (var classeElement in classOrHeaderWithPath.classOrHeader.classList)
            {
                var aditional = new MetricsAditionalData((classeElement.our_class.name as INameInterface)?.GetWrittenName(), classeElement.path);
                CalculateClassMatrics(classeElement.our_class, aditional, classOrHeaderWithPath, null);
                filler.classMetrics.Add(aditional); // check for abstract constructor, protected contained fields!
            }
        }

        private static void CalculateClassMethodAndComplexityFunctionDefinition(FunctionDefinition definition,
            MetricsAditionalData filler, ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            if (Metrics.IsConstructor(definition))
            {
                filler.numberOfConstructors++;
            }

            CallFromDifferentClassesCount(filler, definition, classOrHeaderWithPath);


            filler.numberOfMethods++;
            filler.totalComplexity += definition.cyclomaticComplexity;
            var listOfDeclarations = new ArrayList();
            if (definition.body is not {Count: > 0}) return;
            foreach (var element in definition.body)
            {
                switch (element)
                {
                    case FieldReference:
                        filler.numberOfAccessedAttributes++;
                        break;
                    case DeclWithParent parent when parent.declaration is not DeclarationStatement:
                    case DeclWithParent withParent
                        when (withParent.declaration as DeclarationStatement)?.declarations == null:
                        continue;
                    case DeclWithParent parent:
                    {
                        foreach (var declaration in (parent.declaration as DeclarationStatement)?.declarations!)
                        {
                            if (declaration is not SimpleDeclaration) continue;
                            var name = ((declaration as SimpleDeclaration).declSpecifier as INameInterface)
                                ?.GetWrittenName();
                            if (!listOfDeclarations.Contains(name))
                            {
                                listOfDeclarations.Add(name);
                            }
                        }

                        break;
                    }
                }
            }

            filler.numberOfattributesDifferentClass = listOfDeclarations.Count;
            listOfDeclarations.Clear();
        }

        public static void CallFromDifferentClassesCount(MetricsAditionalData filler, FunctionDefinition definition,
            ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            if (classOrHeaderWithPath.ListOfComposition == null ||
                classOrHeaderWithPath.ListOfComposition.Count <= 0) return;
            foreach (var compositionClass in classOrHeaderWithPath.ListOfComposition)
            {
                CheckExtern(compositionClass.path, compositionClass.classOrHeader.methodsWithFunctionCalls, definition, filler);
                

                CheckInClass(compositionClass.classOrHeader.classList, definition, filler);
            }
        }

        private static void CheckInClass(IEnumerable<ComplexCompositeTypeSpecifier> classList,
            FunctionDefinition definition,
            MetricsAditionalData filler)
        {
            var counter = 0;
            foreach (var elemInClass in classList)
            {
                
                foreach (var declarationInClass in elemInClass.our_class.declarations)
                {
                    var name = "";
                    if (declarationInClass is FunctionDefinition)
                    {
                        foreach (var elementInFunctionClassDefinition in
                                 (declarationInClass as FunctionDefinition).body)
                        {
                            if (elementInFunctionClassDefinition is FunctionCalls)
                            {
                                name = (((elementInFunctionClassDefinition as FunctionCalls).name as FieldReference)!
                                    .fieldName as INameInterface)!.GetWrittenName();
                                if (name.Equals(
                                        ((definition.declarator[0] as FunctionDeclarator)!.name as INameInterface)!
                                        .GetWrittenName()))
                                {
                                    counter++;
                                }
                            }
                        }
                    }
                    if (counter > 0)
                    {
                        filler.numberOfClassesThatCallsMethodX.Add(new Tuple<string, string, int>(elemInClass.path +" "+ (elemInClass.our_class.name as INameInterface)!.GetWrittenName(),
                            (((declarationInClass as FunctionDefinition)!.declarator[0] as FunctionDeclarator).name as INameInterface)!.GetWrittenName(),
                            counter));
                        counter = 0;
                    }
                }

                
            }
        }

        private static void CheckExtern(string compositionClassPath,
            ICollection<FunctionDefinition> methodsWithFunctionCalls,
            FunctionDefinition definition, MetricsAditionalData filler)
        {
            
            if (methodsWithFunctionCalls.Count > 0)
            {
                foreach (var methodIntern in methodsWithFunctionCalls)
                {
                    var booleanValue = 0;
                    foreach (var elementInBody in methodIntern.body)
                    {
                        if (elementInBody is not FunctionCalls) continue;
                        if (((definition.declarator[0] as FunctionDeclarator)!.name as INameInterface)!
                            .GetWrittenName() == ((elementInBody as FunctionCalls).name as INameInterface)!
                            .GetWrittenName())
                        {
                            booleanValue ++;
                        }
                    }
                    if (booleanValue > 0)
                    {
                        filler.numberOfClassesThatCallsMethodX.Add(new Tuple<string, string, int>(compositionClassPath,
                            ((methodIntern.declarator[0] as FunctionDeclarator)!.name as INameInterface)!.GetWrittenName(),
                            booleanValue));
                    }
                }
            }
          
        }


        private static void CalculateClassMethodAndComplexitySimpleDeclaration(SimpleDeclaration simpleDeclaration,
            ClassOrHeaderWithPath classOrHeaderWithPath, MetricsAditionalData filler,
            CompositeTypeSpecifier classeElement, NameSpace nameSpace)
        {
            var checkIfItsImplemented = false;
            var abstractMethod =
                simpleDeclaration.declarators[0] as FunctionDeclarator;
            foreach (var functionCall in classOrHeaderWithPath.classOrHeader.methodsWithFunctionCalls)
            {
                if (abstractMethod == null ||
                    (functionCall.declarator[0] as FunctionDeclarator)?.name is not QualifiedName ||
                    !((QualifiedName) (functionCall.declarator[0] as FunctionDeclarator)?.name)!.GetWrittenName()
                        .Equals((abstractMethod.name as INameInterface)?.GetWrittenName())) continue;
                var boolean = false;
                foreach (var qualifiers in ((functionCall.declarator[0] as FunctionDeclarator)?.name as QualifiedName)!
                         .qualifier)
                {
                    if (nameSpace != null)
                    {
                        if (((INameInterface) qualifiers).GetWrittenName().Equals(nameSpace.name))
                        {
                            boolean = true;
                        }
                    }

                    if (((INameInterface) classeElement.name).GetWrittenName()
                        .Equals(((INameInterface) qualifiers).GetWrittenName()))
                    {
                        if (nameSpace == null)
                        {
                            filler.totalComplexity += functionCall.cyclomaticComplexity;
                            checkIfItsImplemented = true;
                        }
                        else
                        {
                            if (boolean)
                            {
                                checkIfItsImplemented = true;
                                filler.numberOfMethods += 1;
                                filler.totalComplexity += functionCall.cyclomaticComplexity;
                            }
                        }
                    }
                }
            }

            if (!checkIfItsImplemented)
            {
                filler.numberOfMethods += 1;
            }
        }
    }
}