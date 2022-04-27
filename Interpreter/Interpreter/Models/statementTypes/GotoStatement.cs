namespace Interpreter.Models{

    public class GotoStatement : IStatement
    {
        public string jumpTo { set; get; }
    }

};