using System.Collections.Generic;

namespace Interpreter.Models.serialize.complexStatementTypes
{

    public class ClassOrHeaderWithPath : IStatement
    {
        public string path;

        public ClassOrHeader classOrHeader;

        public IList<ClassOrHeaderWithPath> ListOfInheritance = new List<ClassOrHeaderWithPath>();

        public IList<ClassOrHeaderWithPath> ListOfDeclaration = new List<ClassOrHeaderWithPath>();

    }

}