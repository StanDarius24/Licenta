using System;
using Interpreter.services;
using Interpreter.Utility;

namespace Interpreter
{
    internal class Program
    {
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