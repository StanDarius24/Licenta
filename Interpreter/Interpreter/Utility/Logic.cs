
using System.Linq;
using Interpreter.Models.serialize;
using Interpreter.services;

namespace Interpreter.Utility{
    public class Logic
    {
        public static void Solve(string data)
        {
            var project = new Project
            {
                name = data.Split(OperatingSystem.GetSeparator()).Last()
            };
            var listOfSolutions =
                Reader.FindProjectData(@data);

            foreach (var solutionPath in listOfSolutions)
            {
                var solution = new Solution();
                var text = Reader.ReadFromPath(solutionPath);
                DataRegistry.deserializedData = Deserializer.DeserializeData(text);
                DirectiveFinder.LinkDirective();
                DataMapper.LinkClasses();
                foreach (var repositoryModel in DataRegistry.deserializedData)
                {
                    solution.deserializedData.Add(repositoryModel);
                }
                project.solution.Add(solution);
            }
        }    
    }
};