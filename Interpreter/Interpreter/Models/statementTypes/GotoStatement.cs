namespace Interpreter.Models.statementTypes{

    public class GotoStatement : IStatement
    {
        public string jumpTo { set; get; }
    }

};