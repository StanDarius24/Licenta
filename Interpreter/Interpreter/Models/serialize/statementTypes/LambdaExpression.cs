using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{

    public class LambdaExpression : IStatement
    {
        public IStatement captureDefault { set; get; }

        public IList<IStatement> captures = new List<IStatement>();
        
        public IStatement declarator { set; get; }
        
        public IStatement body { set; get; }
        
        public IStatement clousureTypeName { set; get; }

        public IList<IStatement> implicitFunctionCallName = new List<IStatement>();

    }

};