using System.Collections.Generic;

namespace Interpreter.Models{

    public class ExpressionList: IStatement
    {
        public IList<IStatement> expression = new List<IStatement>();
    }

};