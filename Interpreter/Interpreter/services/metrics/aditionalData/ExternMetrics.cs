using System;
using System.Collections;
using Interpreter.Models.metrics;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.services.metrics.aditionalData
{
    public class ExternMetrics
    {
        public static void CalculateExternMethodAndComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsInFile filler)
        {
            if (filler.ExternalClasses == null)
            {
                filler.ExternalClasses = new MetricsAditionalData("extern", classOrHeaderWithPath.path);
            }

            foreach (var functionDefinition in classOrHeaderWithPath.classOrHeader.methodsWithFunctionCalls)
            {
                CalculateFunctionDefinition(filler, functionDefinition, classOrHeaderWithPath);
            }
        }

        public static void CalculateFunctionDefinition(MetricsInFile filler, FunctionDefinition functionDefinition,
            ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            CheckDeclaration(filler, functionDefinition);
            CheckBody(filler, functionDefinition);
            CheckExtern(filler, functionDefinition, classOrHeaderWithPath);
        }

        private static void CheckExtern(MetricsInFile filler, FunctionDefinition functionDefinition,
            ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            CallFromDifferentClassesCount(filler, functionDefinition, classOrHeaderWithPath);
            CheckIfOverridesMethod(filler.ExternalClasses, functionDefinition, classOrHeaderWithPath);
        }

        private static void CheckBody(MetricsInFile filler, FunctionDefinition functionDefinition)
        {
            CountNumberOfAttributesOutsiteClass(filler, functionDefinition);
        }

        private static void CheckDeclaration(MetricsInFile filler, FunctionDefinition functionDefinition)
        {
            CheckConstructor(filler, functionDefinition);
            NumberOfMethodsAndComplexity(filler, functionDefinition);
        }

        private static void CheckConstructor(MetricsInFile filler, FunctionDefinition functionDefinition)
        {
            if (MethodService.IsConstructor(functionDefinition))
            {
                filler.ExternalClasses.numberOfConstructors++;
            }
        }

        private static void NumberOfMethodsAndComplexity(MetricsInFile filler, FunctionDefinition functionDefinition)
        {
            if ((functionDefinition.declarator[0] as FunctionDeclarator)?.name is Name)
            {
                filler.ExternalClasses.numberOfMethods++;
                filler.ExternalClasses.totalComplexity += functionDefinition.cyclomaticComplexity;
                if ((functionDefinition.declarator[0] as FunctionDeclarator)!.modifier is "protected:" or "protected")
                {
                    filler.ExternalClasses.numberOfProtectedMethods++;
                }
            }
        }

        private static void CountNumberOfAttributesOutsiteClass(MetricsInFile filler,
            FunctionDefinition functionDefinition)
        {
            var listOfDeclarations = new ArrayList();
            foreach (var elementInBody in functionDefinition.body)
            {
                if (elementInBody is FunctionCallsWithDeclaration)
                {
                    filler.ExternalClasses.numberOfMethodCalls++;
                }

                if (elementInBody is FieldReferenceWithParent)
                {
                    filler.ExternalClasses.numberOfFieldReferenceFromClass++;
                    if ((elementInBody as FieldReferenceWithParent).parent != null)
                    {
                        if ((elementInBody as FieldReferenceWithParent).parent is DeclarationStatement)
                        {
                            if (((DeclarationStatement) (elementInBody as FieldReferenceWithParent).parent).declarations
                                    [0] is SimpleDeclaration declaration)
                            {
                                if (!listOfDeclarations.Contains(((INameInterface) declaration.declSpecifier)
                                        .GetWrittenName()))
                                {
                                    listOfDeclarations.Add(
                                        ((INameInterface) declaration.declSpecifier).GetWrittenName());
                                    filler.ExternalClasses.numberOfClassesFieldReference++;
                                }
                            }
                        }
                        else if ((elementInBody as FieldReferenceWithParent).parent is SimpleDeclaration
                                 simpleDeclaration)
                        {
                            if (!listOfDeclarations.Contains(((INameInterface) simpleDeclaration.declSpecifier)
                                    .GetWrittenName()))
                            {
                                listOfDeclarations.Add(((INameInterface) simpleDeclaration.declSpecifier)
                                    .GetWrittenName());
                                filler.ExternalClasses.numberOfClassesFieldReference++;
                            }
                        }
                        else if ((elementInBody as FieldReferenceWithParent).parent is ParameterDeclaration parameterDeclaration)
                        {
                            if (!listOfDeclarations.Contains(
                                    ((INameInterface) parameterDeclaration.declarationSpecifier).GetWrittenName()))
                            {
                                listOfDeclarations.Add(((INameInterface) parameterDeclaration.declarationSpecifier)
                                    .GetWrittenName());
                                filler.ExternalClasses.numberOfClassesFieldReference++;
                            }
                        }
                        
                    }
                }
            }
        }


        private static void CallFromDifferentClassesCount(MetricsInFile filler, FunctionDefinition functionDefinition,
            ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            
        }

        public static void CheckIfOverridesMethod(MetricsAditionalData filler, FunctionDefinition functionDefinition,
            ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            var boolInterionr = false;
            if (classOrHeaderWithPath.ListOfInheritance != null && classOrHeaderWithPath.ListOfInheritance.Count > 0)
            {
                foreach (var inheritance in classOrHeaderWithPath.ListOfInheritance)
                {
                    foreach (var classInheritanceList in inheritance.classOrHeader.classList)
                    {
                        if (functionDefinition.declarator[0] is FunctionDeclarator &&
                            (functionDefinition.declarator[0] as FunctionDeclarator)!.name is QualifiedName)
                        {
                            foreach (var qualiElement in
                                     ((functionDefinition.declarator[0] as FunctionDeclarator)!.name as QualifiedName)!
                                     .qualifier)
                            {
                                if ((qualiElement as Name)!.name.Equals(
                                        (classInheritanceList.our_class.name as INameInterface)?.GetWrittenName()))
                                {
                                    boolInterionr = true;
                                }
                            }
                        }
                    }
                }
            }

            if (boolInterionr)
            {
                filler.numberOfOverridingMethods++;
            }
        }
    }
};