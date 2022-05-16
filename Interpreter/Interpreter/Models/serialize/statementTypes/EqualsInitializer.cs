using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{

    public class EqualsInitializer: IStatement
    {
        public IStatement functionName { set; get; }

        public IList<IStatement> statements = new List<IStatement>();

    }

};