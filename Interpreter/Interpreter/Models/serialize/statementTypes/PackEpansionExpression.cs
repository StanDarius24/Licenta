namespace Interpreter.Models.statementTypes{

    public class PackEpansionExpression : IStatement
    {
        public IStatement pattern { set; get; }
    }

};