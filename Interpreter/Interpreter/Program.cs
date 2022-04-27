﻿using System;
using System.Collections.Generic;
using Interpreter.Models;
using Interpreter.Utility;
using Newtonsoft.Json;

namespace Interpreter
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            var text = Reader.ReadFromPath(
                @"C:\Users\Stannis\Desktop\KotlinLicenta\src\main\resources\c++\result\project64-develop\vstudio.sln.json");
            Console.WriteLine(text);
            var data = JsonConvert.DeserializeObject<IList<SolutionModel>>(text, new JsonSerializerSettings
            {
                TypeNameHandling = TypeNameHandling.Auto
            });
            Console.WriteLine(text);
        }
    }
}