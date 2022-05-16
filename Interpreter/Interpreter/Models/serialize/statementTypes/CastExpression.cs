namespace Interpreter.Models.serialize.statementTypes{

    public class CastExpression: IStatement
    {
        public IStatement operand { set; get; }
        
        public IStatement typeId { set; get; }
        
    }

};