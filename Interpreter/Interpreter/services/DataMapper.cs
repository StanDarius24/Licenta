using System;
using Interpreter.Models.serialize;
using Interpreter.Models.serialize.complexStatementTypes;

namespace Interpreter.services{
    public static class DataMapper
    {
        public static void LinkClasses()
        {
            foreach (var variable in DataRegistry.deserializedData)
            {
                LookUpForRepositoryModel(variable);
            }
        }

        private static void LookUpForRepositoryModel(RepositoryModel repositoryModel)
        {
            foreach (var cppFile in repositoryModel.listOfCppFiles)
            {
                SolveCppOrHeaderFile(cppFile);
            }

            foreach (var headerFile in repositoryModel.listOfHeaderFiles)
            {
                SolveCppOrHeaderFile(headerFile);
            }
        }

        private static void SolveCppOrHeaderFile(ClassOrHeaderWithPath cppOrHeaderFile)
        {
            foreach (var classData in cppOrHeaderFile.classOrHeader.classList)
            {
                foreach (var superClass in classData.our_class.baseSpecifier)
                {
                    SolveSuperClass(superClass);
                }
            }
        }

        private static void SolveSuperClass(IStatement superClass)
        {
            Console.Out.Write("test");
        }
    }
}