using System.Collections.Generic;

namespace Interpreter.Models
{

    public class VcxProjStructure
    {
        public string path;

        public IList<string> listOfCppFiles = new List<string>();

        public IList<string> listOfHeaderFiles = new List<string>();

        public IList<string> listOfIncludedModules = new List<string>();

        public IList<string> listOfUnusedFiles = new List<string>();

    }

};