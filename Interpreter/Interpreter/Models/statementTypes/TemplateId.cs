using System.Collections.Generic;

namespace Interpreter.Models{
    public class TemplateId : IStatement
    {
        public IStatement templateName { set; get; }

        public IList<IStatement> templateArguments = new List<IStatement>();
    }
}