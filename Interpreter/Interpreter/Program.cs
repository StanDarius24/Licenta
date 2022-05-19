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
            Logic.Solve(@"C:\Users\Stannis\Desktop\KotlinLicenta\c++\project64-develop");
        }
    }
}