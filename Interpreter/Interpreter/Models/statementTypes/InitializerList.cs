using System.Collections.Generic;

namespace Interpreter.Models{

    public class InitializerList : IStatement
    {
        public IList<IStatement> initializers = new List<IStatement>();
    }

};