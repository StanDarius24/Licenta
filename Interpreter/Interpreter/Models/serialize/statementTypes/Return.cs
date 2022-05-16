namespace Interpreter.Models.serialize.statementTypes{
 
    public class Return : IStatement
    {
        public IStatement retValue { set; get; }
    }
}