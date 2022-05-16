namespace Interpreter.Models.serialize.statementTypes{
    public class UnaryExpression : IStatement
    {
        public IStatement operand { set; get; }
    }
};