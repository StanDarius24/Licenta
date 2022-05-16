namespace Interpreter.Models.serialize.statementTypes{

public class ConditionalExpression: IStatement
{
    public IStatement condition { set; get; }
    
    public IStatement positiveResult { set; get; }
    
    public IStatement negativeResult { set; get; }
    
}

};