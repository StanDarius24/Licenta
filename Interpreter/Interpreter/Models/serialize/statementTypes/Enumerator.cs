namespace Interpreter.Models.serialize.statementTypes{

    public class Enumerator: IStatement
    {
        public IStatement name { set; get; }
        
        public IStatement value { set; get; }
        
    }

};