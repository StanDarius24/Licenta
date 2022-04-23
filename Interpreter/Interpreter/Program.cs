using System;
using Interpreter.Utility;
using Newtonsoft.Json;

namespace Interpreter
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            var text = Reader.ReadFromPath(
                "/home/stan/Desktop/Licenta/src/main/resources/result/project64-develop/vstudio.sln.json");
            Console.WriteLine(text);
            var data = JsonConvert.DeserializeObject<Object>(text);
            Console.WriteLine(text);
        }
    }
}