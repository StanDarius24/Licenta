using System.Collections.Generic;

namespace Interpreter.Models.statementTypes{

    public class FunctionCall : IStatement
    {
        public string name { set; get; }

        public IList<IStatement> arguments = new List<IStatement>();

    }

};