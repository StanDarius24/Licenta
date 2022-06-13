using System;
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
                
                AverageMethodWeight.CalculateAmw(filler);
                WeightedMethodCount.CalculateWmc(filler);
                NumberOfMethods.CalculateNom(filler);
                NumberOfPublicAttributes.CalculateNopa(filler);
                NumberOfProtectedMembers.CalculateNopm(filler);
                // AccessToForeignData.CalculateAtfd(filler); not sure
                // AccessToForeignData2.CalculateAtfd2(filler);
                ForeignDataProvider.CalculateFdp(filler);
                WeightOfaClass.CalculateWoc(filler);
                BaseClassOverridingRatio.CalculateBOvR(filler);
                ChangingClasses.CalculateCc(filler); 
                Console.Out.Write("test");
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

        private static MetricsInFile CalculateNumberOfMethodsAndCyclomaticComplexity(
            ClassOrHeaderWithPath classOrHeaderWithPath)
        {
            var metricsInFile = new MetricsInFile();
            ClassMethodComplexity.CalculateClassMethodAndComplexity(classOrHeaderWithPath, metricsInFile); // modify
            NameSpaceMetrics.CalculateNameSpaceMethodsComplexity(classOrHeaderWithPath, metricsInFile); // modify
            ExternMetrics.CalculateExternMethodAndComplexity(classOrHeaderWithPath, metricsInFile);
            return metricsInFile;
        }
    }
}