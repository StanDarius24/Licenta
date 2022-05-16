using System.Collections.Generic;

namespace Interpreter.Models.statementTypes
{

    public class FunctionDefinition : IStatement
    {

        public IStatement declaratorSpecifier;

        public IList<IStatement> declarator = new List<IStatement>();

        public IList<IStatement> body = new List<IStatement>();

    }

};