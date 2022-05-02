namespace Interpreter.Models.statementTypes{
    public class UnaryExpression : IStatement
    {
        public IStatement operand { set; get; }
    }
};