using System.Collections.Generic;

namespace Interpreter.Models{
    public class TemplateDeclaration: IStatement
    {
        public IStatement declaration { set; get; }
        
        public IStatement templateScope { set; get; }

        public IList<IStatement> parameters = new List<IStatement>();

    }
}