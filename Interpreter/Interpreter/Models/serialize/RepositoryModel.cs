using System.Collections.Generic;
using Interpreter.Models.complexStatementTypes;

namespace Interpreter.Models{
    public class RepositoryModel : IStatement
    {
            public VcxprojStructure vcxprojStructure { get; set; }

            public IList<ClassOrHeaderWithPath> listOfHeaderFiles { get; set; } = new List<ClassOrHeaderWithPath>();

            public IList<ClassOrHeaderWithPath> listOfCppFiles { get; set; } = new List<ClassOrHeaderWithPath>();
    }
};