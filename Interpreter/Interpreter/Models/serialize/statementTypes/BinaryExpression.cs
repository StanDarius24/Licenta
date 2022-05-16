namespace Interpreter.Models.serialize.statementTypes{

public class BinaryExpression: IStatement
{
  
  public IStatement leftExpression { set; get; }
  
  public IStatement rightExpression { set; get; }
  
}

};