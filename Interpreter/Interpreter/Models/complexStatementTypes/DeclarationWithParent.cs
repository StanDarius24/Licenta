namespace Interpreter.Models
{

    public class DeclarationWithParent : IStatement
    {
        public SimpleDeclaration declaration;

        public IStatement parent;

    }
}