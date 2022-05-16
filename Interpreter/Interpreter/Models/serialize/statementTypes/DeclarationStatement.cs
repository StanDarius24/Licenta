using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{

public class DeclarationStatement: IStatement
{
    public IList<IStatement> declarations = new List<IStatement>();
}

};