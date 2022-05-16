using System.Collections.Generic;

namespace Interpreter.Models.statementTypes
{

    public class FunctionDeclarator : IStatement
    {
        public IList<IStatement> parameter = new List<IStatement>();

        public IStatement name;
    }
};