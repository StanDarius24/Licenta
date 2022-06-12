using System;
using System.Collections;
using Interpreter.Models.metrics;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;
using Interpreter.Utility;

namespace Interpreter.services.metrics
{
    public class ExternMetrics
    {
        public static void CalculateExternMethodAndComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsInFile filler)
        {
            if (filler.ExternalClasses == null)
            {
                filler.ExternalClasses = new MetricsAditionalData();
            }

            foreach (var functionDefinition in classOrHeaderWithPath.classOrHeader.methodsWithFunctionCalls)
            {
                CalculateFunctionDefinition(filler, functionDefinition, classOrHeaderWithPath);
            }
        }

        public static void CalculateFunctionDefinition(MetricsInFile filler, FunctionDefinition functionDefinition,
            ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            if (Metrics.IsConstructor(functionDefinition))
            {
                filler.ExternalClasses.numberOfConstructors++;
            }

            if ((functionDefinition.declarator[0] as FunctionDeclarator)?.name is Name)
            {
                filler.ExternalClasses.numberOfMethods++;
                filler.ExternalClasses.totalComplexity += functionDefinition.cyclomaticComplexity;
            }

            var listOfDeclarations = new ArrayList();
            foreach (var elementInBody in functionDefinition.body)
            {
                if (elementInBody is not DeclWithParent) continue;
                if ((elementInBody as DeclWithParent).declaration is not DeclarationStatement) continue;
                foreach (var declaration in (((elementInBody as DeclWithParent).declaration as DeclarationStatement)!)
                         .declarations)
                {
                    if (declaration is not SimpleDeclaration) continue;
                    if ((declaration as SimpleDeclaration).declSpecifier is not INameInterface) continue;
                    var name = ((declaration as SimpleDeclaration).declSpecifier as INameInterface)!
                        .GetWrittenName();
                    if (!listOfDeclarations.Contains(name))
                    {
                        listOfDeclarations.Add(name);
                    }
                }
            }

            if (functionDefinition.declarator[0] is FunctionDeclarator)
            {
                foreach (var param in (functionDefinition.declarator[0] as FunctionDeclarator)!.parameter)
                {
                    var name = ((INameInterface) ((ParameterDeclaration) param).declarationSpecifier).GetWrittenName();
                    if (!listOfDeclarations.Contains(name))
                    {
                        listOfDeclarations.Add(name);
                    }
                }
            }

            filler.ExternalClasses.numberOfattributesDifferentClass = listOfDeclarations.Count;
            listOfDeclarations.Clear();

            CheckIfOverridesMethod(filler.ExternalClasses, functionDefinition, classOrHeaderWithPath);
        }

        public static void CheckIfOverridesMethod(MetricsAditionalData filler, FunctionDefinition functionDefinition, ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            var boolInterionr = false;
            if (classOrHeaderWithPath.ListOfInheritance != null && classOrHeaderWithPath.ListOfInheritance.Count > 0)
            {
                foreach (var inheritance in classOrHeaderWithPath.ListOfInheritance)
                {
                    foreach (var classInheritanceList in inheritance.classOrHeader.classList)
                    {
                        if (functionDefinition.declarator[0] is FunctionDeclarator && (functionDefinition.declarator[0] as FunctionDeclarator)!.name is QualifiedName)
                        {
                            foreach (var qualiElement in (((functionDefinition.declarator[0] as FunctionDeclarator)!.name as QualifiedName)!).qualifier )
                            {
                                if ((qualiElement as Name)!.name.Equals((classInheritanceList.our_class.name as INameInterface)?.GetWrittenName()))
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