namespace Interpreter.Models.statementTypes{

    public class CastExpression: IStatement
    {
        public IStatement operand { set; get; }
        
        public IStatement typeId { set; get; }
        
    }

};