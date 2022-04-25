using System.Collections.Generic;

namespace Interpreter.Models{

public class DeclarationStatement: IStatement
{
    public IList<IStatement> declarations = new List<IStatement>();
}

};