namespace Interpreter.Models.serialize.statementTypes{

    public class FieldReference : IStatement, INameInterface
    {
        public IStatement fieldName { set; get; }
        
        public IStatement fieldOwner { set; get; }

        public string GetWrittenName()
        {
            return fieldName is INameInterface ? (fieldName as INameInterface).GetWrittenName() : "";
        }
    }

};