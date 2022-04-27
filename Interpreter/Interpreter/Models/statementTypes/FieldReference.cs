namespace Interpreter.Models{

    public class FieldReference : IStatement
    {
        public IStatement fieldName { set; get; }
        
        public IStatement fieldOwner { set; get; }
        
    }

};