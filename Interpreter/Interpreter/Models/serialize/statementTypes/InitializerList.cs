using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{

    public class InitializerList : IStatement
    {
        public IList<IStatement> initializers = new List<IStatement>();
    }

};