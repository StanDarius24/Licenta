using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes
{

    public class FunctionDefinition : IStatement
    {

        public IStatement declaratorSpecifier;

        public IList<IStatement> declarator = new List<IStatement>();

        public IList<IStatement> body = new List<IStatement>();
        
        public int cyclomaticComplexity { set; get; }

    }

};