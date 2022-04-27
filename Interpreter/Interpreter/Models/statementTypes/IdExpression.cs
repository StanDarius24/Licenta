namespace Interpreter.Models{

    public class IdExpression : IStatement
    {
        public IStatement expression { set; get; }
    }

};