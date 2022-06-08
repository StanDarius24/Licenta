using System.Collections;
using System.Collections.Generic;
using Interpreter.Models.metrics;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.services.metrics
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

        public static bool IsConstructor(FunctionDefinition functionDefinition)
        {
            if (functionDefinition.declaratorSpecifier is FunctionDefinition)
            {
                IsConstructor(functionDefinition.declaratorSpecifier as FunctionDefinition);
            }
            else if (functionDefinition.declaratorSpecifier is SimpleDeclSpecifier)
            {
                return ((SimpleDeclSpecifier) functionDefinition.declaratorSpecifier).declarationSpecifier.Equals("");
            } else if (functionDefinition.declaratorSpecifier is INameInterface)
            {
                return ((INameInterface) functionDefinition.declaratorSpecifier).GetWrittenName().Equals("");
            }
            return false;

        }

        private static ArrayList CalculateNumberOfMethodsAndCyclomaticComplexity(
            ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            var fillersClass = new MetricsAditionalData();
            var fillersExtern = new MetricsAditionalData();
            var fillersNamespace = new MetricsAditionalData();
            ClassMethodComplexity.CalculateClassMethodAndComplexity(classOrHeaderWithPath, fillersClass);
            NameSpaceMetrics.CalculateNameSpaceMethodsComplexity(classOrHeaderWithPath, fillersNamespace);
            ExternMetrics.CalculateExternMethodAndComplexity(classOrHeaderWithPath, fillersExtern);
            return new ArrayList
            {
                fillersClass,
                fillersExtern,
                fillersNamespace
            };
        }
    }
}