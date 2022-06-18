using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.Models.serialize.complexStatementTypes{
    public class FieldReferenceWithParent : IStatement
    {
        public FieldReference fieldReference { set; get; }
        
        public IStatement parent { set; get; }
    }
}