using System.Collections.Generic;

namespace Interpreter.Models.statementTypes{
 
    public class QualifiedName : IStatement
    {
        public IList<IStatement> qualifier = new List<IStatement>();
        
        public IStatement lastName { set; get; }
        
    }
}