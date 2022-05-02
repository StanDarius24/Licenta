using System.Collections.Generic;

namespace Interpreter.Models.statementTypes
{

    public class SimpleDeclaration : IStatement
    {

        public IList<IStatement> declarators = new List<IStatement>();

        public IStatement declSpecifier;

    }

};