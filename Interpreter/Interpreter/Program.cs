using System;
using System.Diagnostics.CodeAnalysis;
using Interpreter.services;
using Interpreter.Utility;

namespace Interpreter
{
    internal class Program
    {
        [SuppressMessage("ReSharper.DPA", "DPA0001: Memory allocation issues")]
        public static void Main(string[] args)
        {
            var text = Reader.ReadFromPath(
                @"C:\Users\Stannis\Desktop\KotlinLicenta\c++\result\ConsoleApplication\ConsoleApplication.sln.json");
            DataRegistry.deserializedData = Deserializer.DeserializeData(text);
            DirectiveFinder.LinkDirective();
            DataMapper.LinkClasses();
            Console.WriteLine(text);
        }
    }
}