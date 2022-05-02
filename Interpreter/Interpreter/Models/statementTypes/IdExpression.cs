namespace Interpreter.Models.statementTypes{

    public class IdExpression : IStatement
    {
        public IStatement expression { set; get; }
    }

};