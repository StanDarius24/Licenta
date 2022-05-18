namespace Interpreter.Models.serialize.statementTypes{
    public class Capture: IStatement
    {
           public bool byReference { set; get; }
           
           public IStatement identifier { set; get; }
    }
}