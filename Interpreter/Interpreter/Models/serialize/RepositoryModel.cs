using System.Collections.Generic;
using Interpreter.Models.serialize.complexStatementTypes;

namespace Interpreter.Models.serialize{
    public class RepositoryModel : IStatement
    {
            public VcxprojStructure vcxprojStructure { get; set; }

            public IList<ClassOrHeaderWithPath> listOfHeaderFiles { get; set; } = new List<ClassOrHeaderWithPath>();

            public IList<ClassOrHeaderWithPath> listOfCppFiles { get; set; } = new List<ClassOrHeaderWithPath>();
    }
};