using Interpreter.Models.metrics;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.services.metrics{
    public class ClassMethodComplexity
    {

        public static void CalculateClassMatrics(CompositeTypeSpecifier classeElement, MetricsAditionalData filler, ClassOrHeaderWithPath classOrHeaderWithPath, NameSpace nameSpace)
        {
            foreach (var declaration in classeElement.declarations)
            {
                switch (declaration)
                {
                    case FunctionDefinition definition:
                    {
                        CalculateClassMethodAndComplexityFunctionDefinition(definition, filler);
                        break;
                    }
                    case SimpleDeclaration simpleDeclaration when simpleDeclaration.declarators[0] is Declarator:
                        CheckFieldType(simpleDeclaration, filler);
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
                if (((Declarator) declarator).modifier.Equals("public"))
                {
                    filler.numberOfPublicFields++;
                } else if(((Declarator) declarator).modifier.Equals("protected"))
                {
                    filler.numberOfProtectedMethodsFields++;
                }
            }
        }

        public static void CalculateClassMethodAndComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsAditionalData filler)
        {
            foreach (var classeElement in classOrHeaderWithPath.classOrHeader.classList)
            {
                CalculateClassMatrics(classeElement.our_class, filler, classOrHeaderWithPath, null);
            }
        }
        
        private static void CalculateClassMethodAndComplexityFunctionDefinition(FunctionDefinition definition,
            MetricsAditionalData filler)
        {
            if (Metrics.IsConstructor(definition))
            {
                filler.numberOfConstructors++;
            }

            filler.numberOfMethods++;
            filler.totalComplexity += definition.cyclomaticComplexity;
        }
        
        private static void CalculateClassMethodAndComplexitySimpleDeclaration(SimpleDeclaration simpleDeclaration,
            ClassOrHeaderWithPath classOrHeaderWithPath, MetricsAditionalData filler,
            CompositeTypeSpecifier classeElement, NameSpace nameSpace)
        {
            var checkIfItsImplemented = false;
            var abstractMethod =
                simpleDeclaration.declarators[0] as FunctionDeclarator;
            foreach (var functionCall in classOrHeaderWithPath.classOrHeader.functionCallsWithoutImplementation)
            {
                if (abstractMethod == null || functionCall.name is not QualifiedName ||
                    !((QualifiedName) functionCall.name).GetWrittenName()
                        .Equals((abstractMethod.name as INameInterface)?.GetWrittenName())) continue;
                var boolean = false;
                foreach (var qualifiers in (functionCall.name as QualifiedName)!.qualifier)
                {
                    if (nameSpace != null)
                    {
                        if (((INameInterface) qualifiers).GetWrittenName().Equals(nameSpace.name))
                        {
                            boolean = true;
                        }
                    }
                    if (((INameInterface) classeElement.name).GetWrittenName().Equals(((INameInterface) qualifiers).GetWrittenName()))
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