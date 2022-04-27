namespace Interpreter.Models{

    public class CastExpression: IStatement
    {
        public IStatement operand { set; get; }
        
        public IStatement typeId { set; get; }
        
    }

};