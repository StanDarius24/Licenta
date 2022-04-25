using System.Collections.Generic;

namespace Interpreter.Models
{

    public class FunctionDeclarator : IStatement
    {
        public IList<IStatement> parameter = new List<IStatement>();

        public IStatement name;
    }
};