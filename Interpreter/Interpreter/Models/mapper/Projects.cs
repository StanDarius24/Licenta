using System.Collections.Generic;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.Models.mapper{
    public class Projects
    {
        public string Language { get; set; } = "";

        public string Name { get; set; } = "";

        public string FilePath { get; set; } = "";
        
        public IList<string> ProjectReferences { get; set; } = new List<string>();
        
        public IList<NameSpace> Namespaces { get; set; } = new List<NameSpace>();
    }
};