using System.Collections.Generic;

namespace Interpreter.Models.complexStatementTypes
{

    public class TranslationWithPath : IStatement
    {
        public string path;

        public FinalTranslation finalTranslation;

        public IList<TranslationWithPath> relationList = new List<TranslationWithPath>();

    }

};