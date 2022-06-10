using System.Diagnostics.CodeAnalysis;
using Interpreter.Utility;

namespace Interpreter;

internal class Program
{
    [SuppressMessage("ReSharper.DPA", "DPA0001: Memory allocation issues")]
    public static void Main(string[] args)
    {
        Logic.Solve(@"C:\Users\Stannis\Desktop\KotlinLicenta\c++\Testing");
    }
}