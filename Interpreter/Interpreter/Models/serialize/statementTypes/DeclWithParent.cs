namespace Interpreter.Models.serialize.statementTypes{
    public class DeclWithParent : IStatement
    {
        public IStatement declaration { set; get; }
        
        public IStatement parent { set; get; }
    }
}