using System.Collections.Generic;

namespace Interpreter.Models
{

    public class FunctionCalls : IStatement
    {
        
        public IStatement name { set; get; }

        public IList<IStatement> arguments = new List<IStatement>();

    }

}