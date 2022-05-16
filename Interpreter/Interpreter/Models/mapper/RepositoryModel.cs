using System.Collections.Generic;

namespace Interpreter.Models.mapper{
    public class RepositoryModel
    {
        public string RepositoryName { set; get; }
    
        public IList<Solutions> VcxProject = new List<Solutions>();

        public IList<Projects> ProjectModel = new List<Projects>();
    }
}