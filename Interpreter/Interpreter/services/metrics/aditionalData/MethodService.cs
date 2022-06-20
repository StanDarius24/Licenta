using System;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.services.metrics.aditionalData{
    public static class MethodService
    {
        public static bool IsConstructor(FunctionDefinition functionDefinition)
        {
            if (functionDefinition.declaratorSpecifier is FunctionDefinition)
            {
                IsConstructor(functionDefinition.declaratorSpecifier as FunctionDefinition);
            }
            else if (functionDefinition.declaratorSpecifier is SimpleDeclSpecifier)
            {
                return ((SimpleDeclSpecifier) functionDefinition.declaratorSpecifier).declarationSpecifier.Equals("");
            } else if (functionDefinition.declaratorSpecifier is INameInterface)
            {
                return ((INameInterface) functionDefinition.declaratorSpecifier).GetWrittenName().Equals("");
            }
            return false;

        }

        public static bool IsVirtual(FunctionDefinition functionDefinition)
        {
            // Console.WriteLine("Tets");
            return false;
        }
    }
}