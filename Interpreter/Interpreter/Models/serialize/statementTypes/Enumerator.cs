namespace Interpreter.Models.statementTypes{

    public class Enumerator: IStatement
    {
        public IStatement name { set; get; }
        
        public IStatement value { set; get; }
        
    }

};