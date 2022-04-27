namespace Interpreter.Models{
    public class UnaryExpression : IStatement
    {
        public IStatement operand { set; get; }
    }
};