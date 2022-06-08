
using System;
using System.Linq;
using Interpreter.Models.serialize;
using Interpreter.services;
using Interpreter.services.metrics;

namespace Interpreter.Utility{
    public class Logic
    {
        public static void Solve(string data)
        {
            var dirFinder = new DirectiveFinder();
            var project = new Project
            {
                name = data.Split(OperatingSystem.GetSeparator()).Last()
            };
            var listOfSolutions =
                Reader.FindProjectData(data);
            if (listOfSolutions == null)
            {
                JarRunner.RunJar(data);
                listOfSolutions = Reader.FindProjectData(data);
            }

            foreach (var solutionPath in listOfSolutions)
            {
                var solution = new Solution();
                var text = Reader.ReadFromPath(solutionPath);
                DataRegistry.deserializedData = Deserializer.DeserializeData(text);
                dirFinder.LinkDirective();
                foreach (var repositoryModel in DataRegistry.deserializedData)
                {
                    solution.deserializedData.Add(repositoryModel);
                }
                project.solution.Add(solution);
            }
            Metrics.CalculateMetrics();
            Console.Out.Write("test");
        }    
    }
};