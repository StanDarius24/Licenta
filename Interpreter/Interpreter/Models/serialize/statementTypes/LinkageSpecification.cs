using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes
{

    public class LinkageSpecification : IStatement
    {

        public string literal;

        public IList<IStatement> declarations = new List<IStatement>();

    }

};