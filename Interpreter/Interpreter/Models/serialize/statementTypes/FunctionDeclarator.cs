using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes
{

    public class FunctionDeclarator : IStatement
    {
        public IList<IStatement> parameter = new List<IStatement>();

        public IStatement name;
    }
};