using System;
using System.Collections.Generic;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.services{
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
                CalculateNumberOfMethodsAndCyclomaticComplexity(file);
            }
        }

        private static bool IsConstructor(ComplexCompositeTypeSpecifier classToTest, FunctionDefinition functionDefinition)
        {
            return ((INameInterface) classToTest.our_class.name).GetWrittenName().Equals(
                ((functionDefinition.declarator[0] as FunctionDeclarator)?.name as INameInterface)?.GetWrittenName());
        }

        private static void CalculateNumberOfMethodsAndCyclomaticComplexity(ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            unsafe
            {
                var numberOfMethods = 0;
                var numberOfConstructors = 0;
                var numberOfContainedFields = 0;
                var numberOfProtectedMethodsFields = 0;
                var totalComplexity = 0;
                CalculateClassMethodAndComplexity(classOrHeaderWithPath, &numberOfMethods,&totalComplexity, &numberOfConstructors);
                CalculateExternMethodAndComplexity(classOrHeaderWithPath, &numberOfMethods, &totalComplexity);
                CalculateNameSpaceMethodsComplexity(classOrHeaderWithPath, &numberOfMethods, &totalComplexity, &numberOfConstructors);
            }
        }

        private static unsafe void CalculateNameSpaceMethodsComplexity(ClassOrHeaderWithPath classOrHeaderWithPath, int* numberOfMethods, int* totalComplexity, int* numberOfConstructors)
        {
            foreach (var nameSpace in classOrHeaderWithPath.classOrHeader.namespaces)
            {
                foreach (var declaration in nameSpace.declarations)
                {
                    if ((declaration as SimpleDeclaration)?.declSpecifier is CompositeTypeSpecifier)
                    {
                        foreach (var declarationInNamespace in ((declaration as SimpleDeclaration).declSpecifier as CompositeTypeSpecifier)?.declarations!)
                        {
                            
                        }
                    }
                    if (declaration is not FunctionDefinition definition) continue;
                    *(numberOfMethods) += 1;
                    *(totalComplexity) += definition.cyclomaticComplexity;
                }
            }
        }

        private static unsafe void CalculateExternMethodAndComplexity(ClassOrHeaderWithPath classOrHeaderWithPath, int* numberOfMethods, int* totalComplexity)
        {
            foreach (var functionDeclarator in classOrHeaderWithPath.classOrHeader.functionCallsWithoutImplementation)
            {
                (*numberOfMethods) += 1;
                (*totalComplexity) += functionDeclarator.cyclomaticComplexity;
            }
        }

        private static unsafe void CalculateClassMethodAndComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            int* numberOfMethods, int* totalComplexity, int* numberOfConstructors)
        {
            foreach (var classeElement in classOrHeaderWithPath.classOrHeader.classList)
            {
                foreach (var declaration in classeElement.our_class.declarations)
                {
                    if (declaration is not FunctionDefinition definition) continue;
                    if (IsConstructor(classeElement, definition))
                    {
                        (*numberOfConstructors) += 1;
                    }
                    (*numberOfMethods) += 1;
                    (*totalComplexity) += definition.cyclomaticComplexity;
                }
            }
        }
    }
}