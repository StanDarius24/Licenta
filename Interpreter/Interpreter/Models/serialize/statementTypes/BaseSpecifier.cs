namespace Interpreter.Models.serialize.statementTypes{

    public class BaseSpecifier: IStatement
    {
        public bool virtua { set; get; }
        
        public IStatement name { set; get; }
        
    }

};