namespace Interpreter.Models.statementTypes{

    public class NewExpression: IStatement
    {
        public IStatement typeId { set; get; }
        
        public IStatement initializer { set; get; }
        
    }

};