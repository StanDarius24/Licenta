using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{

    public class For : IStatement
    {
        public IList<IStatement> initializer = new List<IStatement>();

        public IList<IStatement> conditionExpr = new List<IStatement>();

        public IList<IStatement> conditionDecl = new List<IStatement>();

        public IList<IStatement> iteration = new List<IStatement>();

        public IList<IStatement> body = new List<IStatement>();

    }

};