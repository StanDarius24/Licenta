namespace Interpreter.Models.statementTypes{

public class BinaryExpression: IStatement
{
  
  public IStatement leftExpression { set; get; }
  
  public IStatement rightExpression { set; get; }
  
}

};