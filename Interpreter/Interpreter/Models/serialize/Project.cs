using System.Collections.Generic;

namespace Interpreter.Models.serialize{
    public class Project
    {
        public string name { set; get; }
        
        public IList<Solution> solution = new List<Solution>();
    }
}