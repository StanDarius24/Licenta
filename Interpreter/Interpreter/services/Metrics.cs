using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using Interpreter.Models.metrics;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.services
{
    public class Metrics
    {
        public static void CalculateMetrics()
        {
            foreach (var repositoryModel in DataRegistry.deserializedData)
            {
                SolveClassOrHeader(repositoryModel.listOfHeaderFiles);
                SolveClassOrHeader(repositoryModel.listOfCppFiles);
            }
        }

        private static void SolveClassOrHeader(IEnumerable<ClassOrHeaderWithPath> repositoryModelListOfCppFiles)
        {
            foreach (var file in repositoryModelListOfCppFiles)
            {
                var filler = CalculateNumberOfMethodsAndCyclomaticComplexity(file);
            }
        }

        private static bool IsConstructor(FunctionDefinition functionDefinition)
        {
            return ((SimpleDeclSpecifier) functionDefinition.declaratorSpecifier).declarationSpecifier.Equals("");
        }

        private static ArrayList CalculateNumberOfMethodsAndCyclomaticComplexity(
            ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            var fillersClass = new MetricsAditionalData();
            var fillersExtern = new MetricsAditionalData();
            var fillersNamespace = new MetricsAditionalData();
            CalculateClassMethodAndComplexity(classOrHeaderWithPath, fillersClass);
            CalculateNameSpaceMethodsComplexity(classOrHeaderWithPath, fillersNamespace);
            CalculateExternMethodAndComplexity(classOrHeaderWithPath, fillersExtern);
            return new ArrayList
            {
                fillersClass,
                fillersExtern,
                fillersNamespace
            };
        }

        private static void CalculateNameSpaceMethodsComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsAditionalData filler)
        {
            foreach (var nameSpace in classOrHeaderWithPath.classOrHeader.namespaces)
            {
                foreach (var declaration in nameSpace.declarations)
                {
                    if ((declaration as SimpleDeclaration)?.declSpecifier is CompositeTypeSpecifier)
                    {
                        foreach (var declarationInNamespace in
                                 ((declaration as SimpleDeclaration).declSpecifier as CompositeTypeSpecifier)
                                 ?.declarations!)
                        {
                            if (declarationInNamespace is not FunctionDefinition) continue;
                            if (IsConstructor(declarationInNamespace as FunctionDefinition))
                            {
                                filler.numberOfConstructors++;
                            }
                            filler.numberOfMethods++;
                            filler.totalComplexity +=
                                ((FunctionDefinition) declarationInNamespace).cyclomaticComplexity;
                        }
                    }

                    if (declaration is not FunctionDefinition definition) continue;
                    filler.numberOfMethods++;
                    filler.totalComplexity += definition.cyclomaticComplexity;
                }
            }
        }

        private static void CalculateExternMethodAndComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsAditionalData filler)
        {
            foreach (var functionDeclarator in classOrHeaderWithPath.classOrHeader.functionCallsWithoutImplementation)
            {
                filler.numberOfMethods++;
                filler.totalComplexity += functionDeclarator.cyclomaticComplexity;
            }
        }

        private static void CalculateClassMethodAndComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsAditionalData filler)
        {
            foreach (var classeElement in classOrHeaderWithPath.classOrHeader.classList)
            {
                foreach (var declaration in classeElement.our_class.declarations)
                {
                    switch (declaration)
                    {
                        case FunctionDefinition definition:
                        {
                            if (IsConstructor(definition))
                            {
                                filler.numberOfConstructors++;
                            }
                            filler.numberOfMethods++;
                            filler.totalComplexity += definition.cyclomaticComplexity;
                            break;
                        }
                        case SimpleDeclaration simpleDeclaration when simpleDeclaration.declarators[0] is not FunctionDeclarator:
                            continue;
                        case SimpleDeclaration simpleDeclaration:
                        {
                            var abstractMethod =
                                simpleDeclaration.declarators[0] as FunctionDeclarator;
                            foreach (var functionCall in classOrHeaderWithPath.classOrHeader.functionCallsWithoutImplementation)
                            {
                                if (abstractMethod == null || functionCall.name is not QualifiedName ||
                                    !((QualifiedName) functionCall.name).GetWrittenName()
                                        .Equals((abstractMethod.name as INameInterface)?.GetWrittenName())) continue;
                                if (classeElement.our_class.name.Equals(
                                        (functionCall.name as QualifiedName)?.qualifier[0]))
                                {
                                    filler.totalComplexity += functionCall.cyclomaticComplexity - 1;
                                }
                            }

                            break;
                        }
                    }
                }
                
            }
        }
    }
}