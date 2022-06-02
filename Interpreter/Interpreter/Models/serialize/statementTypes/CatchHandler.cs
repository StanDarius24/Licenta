namespace Interpreter.Models.serialize.statementTypes{
    public class CatchHandler: IStatement
    {
        public IStatement body { set; get; }
    }
}