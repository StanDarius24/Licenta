namespace Interpreter.Models.serialize.statementTypes{
    public class SwitchStatement : IStatement
    {
        public IStatement controllerExpression { set; get; }
        
        public IStatement body { set; get; }
    }
}