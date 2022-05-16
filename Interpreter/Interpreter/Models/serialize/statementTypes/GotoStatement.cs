namespace Interpreter.Models.serialize.statementTypes{

    public class GotoStatement : IStatement
    {
        public string jumpTo { set; get; }
    }

};