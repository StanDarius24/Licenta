namespace Interpreter.Models.serialize.statementTypes{

    public class FieldReference : IStatement
    {
        public IStatement fieldName { set; get; }
        
        public IStatement fieldOwner { set; get; }
        
    }

};