using System.Collections.Generic;

namespace Interpreter.Models
{
    public class CompositeTypeSpecifier : IStatement
    {
        public IStatement name;

        public IList<IStatement> baseSpecifier = new List<IStatement>();

        public IList<IStatement> declarations = new List<IStatement>();

        public int key;

    }
}