using System.Collections.Generic;

namespace Interpreter.Models.statementTypes
{

    public class LinkageSpecification : IStatement
    {

        public string literal;

        public IList<IStatement> declarations = new List<IStatement>();

    }

};