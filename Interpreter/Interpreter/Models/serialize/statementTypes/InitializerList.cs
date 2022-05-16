using System.Collections.Generic;

namespace Interpreter.Models.statementTypes{

    public class InitializerList : IStatement
    {
        public IList<IStatement> initializers = new List<IStatement>();
    }

};