namespace Interpreter.Models.serialize.statementTypes{

    public class PackEpansionExpression : IStatement
    {
        public IStatement pattern { set; get; }
    }

};