using Interpreter.Models.statementTypes;

namespace Interpreter.Models.complexStatementTypes
{

    public class DeclarationWithParent : IStatement
    {
        public SimpleDeclaration declaration;

        public IStatement parent;

    }
}