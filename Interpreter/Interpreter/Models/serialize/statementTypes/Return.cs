namespace Interpreter.Models.statementTypes{
 
    public class Return : IStatement
    {
        public IStatement retValue { set; get; }
    }
}