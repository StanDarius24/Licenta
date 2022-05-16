using System.Collections.Generic;

namespace Interpreter.Models.mapper{
    public class Solutions
    {
        public string FilePath { set; get; } = "";

        public IList<string> ProjectPaths { get; set; } = new List<string>();
    }
};