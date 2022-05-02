namespace Interpreter.Models.complexStatementTypes
{

    public class DeclarationWithClass : IStatement
    {
        public DeclarationWithParent declarationWIthParent { set; get; }
        
        public IStatement linkedClass { set; get; }
        
    }

}