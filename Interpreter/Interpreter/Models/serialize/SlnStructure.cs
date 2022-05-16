using System.Collections.Generic;

namespace Interpreter.Models{
    public class SlnStructure: IStatement
    {
        public string token { set; get; }
        
        public string name { set; get; }
        
        public string path { set; get; }
        
        public string alias { set; get; }

        public Dictionary<string, string> antebuild = new Dictionary<string, string>();

    }
};