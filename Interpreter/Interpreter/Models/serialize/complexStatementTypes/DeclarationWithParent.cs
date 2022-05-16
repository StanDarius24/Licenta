using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.Models.serialize.complexStatementTypes
{

    public class DeclarationWithParent : IStatement
    {
        public SimpleDeclaration declaration;

        public IStatement parent;

    }
}