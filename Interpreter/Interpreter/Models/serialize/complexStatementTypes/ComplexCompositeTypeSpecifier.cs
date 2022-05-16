using System.Collections.Generic;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.Models.serialize.complexStatementTypes{
    public class ComplexCompositeTypeSpecifier: IStatement
    {
        public CompositeTypeSpecifier our_class;

        public string path;

        public IList<IStatement> declarations = new List<IStatement>();
    
    }
}