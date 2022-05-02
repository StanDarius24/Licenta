using System.Collections.Generic;

namespace Interpreter.Models.statementTypes
{

    public class RangeBaseForStatement : IStatement
    {
        public IList<IStatement> declaration = new List<IStatement>();

        public IList<IStatement> initClause = new List<IStatement>();

        public IList<IStatement> body = new List<IStatement>();
    }

}