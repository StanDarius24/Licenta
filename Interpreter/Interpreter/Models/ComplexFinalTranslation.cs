using System.Collections.Generic;
using Interpreter.Models.complexStatementTypes;

namespace Interpreter.Models{
    public class ComplexFinalTranslation : IStatement
    {
            public VcxprojStructure vcxprojStructure { get; set; }

            public IList<TranslationWithPath> listOfHeaderFiles { get; set; } = new List<TranslationWithPath>();

            public IList<TranslationWithPath> listOfCppFiles { get; set; } = new List<TranslationWithPath>();
    }
};