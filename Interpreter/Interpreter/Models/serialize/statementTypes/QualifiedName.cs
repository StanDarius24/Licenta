using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{
 
    public class QualifiedName : IStatement, INameInterface
    {
        public IList<IStatement> qualifier = new List<IStatement>();
        
        public IStatement lastName { set; get; }


        public string GetWrittenName()
        {
            if (lastName is INameInterface)
            {
                return (lastName as INameInterface).GetWrittenName();
            }
            return "";
        }
    }
}