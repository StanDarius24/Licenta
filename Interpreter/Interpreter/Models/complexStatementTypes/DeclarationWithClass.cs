namespace Interpreter.Models
{

    public class DeclarationWithClass : IStatement
    {
        public DeclarationWithParent declarationWIthParent { set; get; }
        
        public IStatement linkedClass { set; get; }
        
    }

}