namespace Interpreter.Models{
    public class StaticAssertionDeclaration : IStatement
    {
        public IStatement condition { set; get; }
        
        public IStatement message { set; get; }

    }
}