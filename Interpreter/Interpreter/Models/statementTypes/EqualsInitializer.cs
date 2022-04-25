using System.Collections.Generic;

namespace Interpreter.Models{

    public class EqualsInitializer: IStatement
    {
        public IStatement functionName { set; get; }

        public IList<IStatement> Statements = new List<IStatement>();

    }

};