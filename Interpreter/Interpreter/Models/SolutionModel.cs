using System.Collections.Generic;

namespace Interpreter.Models
{

    public class SolutionModel
    {
        public IList<VcxProjStructure> projectsPaths { get; set; } = new List<VcxProjStructure>();

        public IList<TranslationWithPath> listOfHeaderFiles { get; set; } = new List<TranslationWithPath>();

        public IList<TranslationWithPath> listOfCppFiles { get; set; } = new List<TranslationWithPath>();
    }
};
