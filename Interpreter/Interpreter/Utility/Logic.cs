
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
            var listOfSolutions =Reader.FilterData(
                Reader.FindProjectData(data).ToList());
            if (listOfSolutions == null)
            {
                JarRunner.RunJar(data);
                listOfSolutions = Reader.FilterData(
                    Reader.FindProjectData(data).ToList());
            }

            foreach (var solutionPath in listOfSolutions)
            {
                Console.WriteLine("Started analysing " + solutionPath);
                var solution = new Solution();
                var text = Reader.ReadFromPath(solutionPath);
                Console.WriteLine("The file has been read " + solutionPath);
                DataRegistry.deserializedData = Deserializer.DeserializeData(text);
                Console.WriteLine("The file has been deserialized " + solutionPath);
                dirFinder.LinkDirective();
                Console.WriteLine("Relations created " + solutionPath);
                foreach (var repositoryModel in DataRegistry.deserializedData)
                {
                    solution.deserializedData.Add(repositoryModel);
                }
                project.solution.Add(solution);
            }
            Metrics.CalculateMetrics(StringService.SolveDataPath(data));
            Console.WriteLine("test");
        }    
    }
};