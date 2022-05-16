namespace Interpreter.Models.serialize.statementTypes{

    public class IdExpression : IStatement
    {
        public IStatement expression { set; get; }
    }

};