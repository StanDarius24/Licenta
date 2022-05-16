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
                @"C:\Users\Stannis\Desktop\KotlinLicenta\src\main\resources\c++\result\project64-develop\Project64.sln.json");
            DataRegistry.deserializedData = Deserializer.DeserializeData(text);
            DirectiveFinder.LinkDirective();
            Console.WriteLine(text);
        }
    }
}