namespace Interpreter.Models.statementTypes{
    public class StaticAssertionDeclaration : IStatement
    {
        public IStatement condition { set; get; }
        
        public IStatement message { set; get; }

    }
}