using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{
    public class TemplateId : IStatement, INameInterface
    {
        public IStatement templateName { set; get; }

        public IList<IStatement> templateArguments = new List<IStatement>();
        public string GetWrittenName()
        {
            return templateName is INameInterface ? (templateName as INameInterface).GetWrittenName() : "";
        }
    }
}