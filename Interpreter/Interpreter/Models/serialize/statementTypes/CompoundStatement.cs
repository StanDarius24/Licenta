using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{

public class CompoundStatement : IStatement
{
    public IList<IStatement> statements = new List<IStatement>();
}

};