using System;
using System.Collections.Generic;
using Interpreter.Models.serialize.complexStatementTypes;

namespace Interpreter.services{
    public class Metrics
    {
        public static void calculateMetrics()
        {
            foreach (var repositoryModel in DataRegistry.deserializedData)
            {
                solveClassOrHeader(repositoryModel.listOfHeaderFiles);
                solveClassOrHeader(repositoryModel.listOfCppFiles);
            }
        }

        private static void solveClassOrHeader(IEnumerable<ClassOrHeaderWithPath> repositoryModelListOfCppFiles)
        {
            foreach (var file in repositoryModelListOfCppFiles)
            {
                Console.Out.Write("test");
            }
        }
    }
}