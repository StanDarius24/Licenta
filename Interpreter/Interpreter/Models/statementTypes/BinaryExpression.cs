namespace Interpreter.Models{

public class BinaryExpression: IStatement
{
  
  public IStatement leftExpression { set; get; }
  
  public IStatement rightExpression { set; get; }
  
}

};