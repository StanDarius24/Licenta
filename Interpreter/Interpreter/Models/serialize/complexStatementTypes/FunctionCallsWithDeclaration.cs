using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.Models.serialize.complexStatementTypes{
    public class FunctionCallsWithDeclaration : IStatement
    {   
        public FunctionCalls functionCalls { set; get; }
        
        public IStatement declWithParent { set; get; }
    }
}