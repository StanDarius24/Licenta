namespace Interpreter.Models{
 
    public class Return : IStatement
    {
        public IStatement retValue { set; get; }
    }
}