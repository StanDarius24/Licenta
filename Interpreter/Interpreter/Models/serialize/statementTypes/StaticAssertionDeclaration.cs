namespace Interpreter.Models.serialize.statementTypes{
    public class StaticAssertionDeclaration : IStatement
    {
        public IStatement condition { set; get; }
        
        public IStatement message { set; get; }

    }
}