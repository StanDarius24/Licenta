using System;
using System.Collections.Generic;
using Interpreter.Models.metrics;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;
using Interpreter.Utility;

namespace Interpreter.services.metrics
{
    public class Metrics
    {
        public static void CalculateMetrics(string solveDataPath)
        {
            foreach (var repositoryModel in DataRegistry.deserializedData)
            {
                Console.Write("Starting metrics calculation for " + repositoryModel.vcxprojStructure.path);
                SolveClassOrHeader(repositoryModel.listOfHeaderFiles, solveDataPath);
                SolveClassOrHeader(repositoryModel.listOfCppFiles, solveDataPath);
            }
        }

        private static void SolveClassOrHeader(IEnumerable<ClassOrHeaderWithPath> repositoryModelListOfCppFiles,
            string solveDataPath)
        {
            foreach (var file in repositoryModelListOfCppFiles)
            {
                Console.Write("Calcualtion for " + file.path);
                var filler = CalculateNumberOfMethodsAndCyclomaticComplexity(file);
                
                AverageMethodWeight.CalculateAmw(filler);
                WeightedMethodCount.CalculateWmc(filler);
                NumberOfMethods.CalculateNom(filler);
                NumberOfPublicAttributes.CalculateNopa(filler);
                NumberOfProtectedMembers.CalculateNopm(filler);
                AccessToForeignData.CalculateAtfd(filler);
                ForeignDataProvider.CalculateFdp(filler);
                WeightOfaClass.CalculateWoc(filler);
                BaseClassOverridingRatio.CalculateBOvR(filler);
                ChangingClassesMethods.CalculateCcCm(filler);
                
                MetricsRegistry.metricsList.Add(filler);
                Exporter.CreateMetricFile(solveDataPath);
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