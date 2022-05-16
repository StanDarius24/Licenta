using System.Collections.Generic;

namespace Interpreter.Models.statementTypes{
    public class NameSpace
    {
        public bool isInline { set; get; }
        
        public string name { set; get; }

        public IList<IStatement> declarations = new List<IStatement>();
    }
};