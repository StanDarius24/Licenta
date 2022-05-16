namespace Interpreter.Models.serialize.statementTypes{

    public class LambdaExpression : IStatement
    {
        public IStatement captureDefault { set; get; }
        
        public IStatement captures { set; get; }
        
        public IStatement declarator { set; get; }
        
        public IStatement body { set; get; }
        
        public IStatement clousureTypeName { set; get; }
        
        public IStatement implicitFunctionCallName { set; get; }
        
    }

};