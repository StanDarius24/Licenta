using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{
    public class NameSpace: IStatement
    {
        public bool isInline { set; get; }
        
        public string name { set; get; }

        public IList<IStatement> declarations = new List<IStatement>();

    }
}