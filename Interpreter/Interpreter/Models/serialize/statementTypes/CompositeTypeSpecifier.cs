using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes
{
    public class CompositeTypeSpecifier : IStatement
    {
        public IStatement name { set; get; }

        public IList<IStatement> baseSpecifier = new List<IStatement>();

        public IList<IStatement> declarations = new List<IStatement>();

        public int key { set; get; }

    }
}