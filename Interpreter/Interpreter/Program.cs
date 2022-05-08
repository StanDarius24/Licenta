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
                @"/home/stan/Desktop/Licenta/src/main/resources/result/project64-develop/vstudio.sln.json");
            DataRegistry.deserializedData = Deserializer.DeserializeData(text);
            DirectiveFinder.LinkDirective();
            Console.WriteLine(text);
        }
    }
}