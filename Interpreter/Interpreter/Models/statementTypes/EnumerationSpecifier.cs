using System.Collections.Generic;

namespace Interpreter.Models{

    public class EnumerationSpecifier: IStatement
    {
        public string name { set; get; }

        public IList<IStatement> enumerators = new List<IStatement>();

    }

};