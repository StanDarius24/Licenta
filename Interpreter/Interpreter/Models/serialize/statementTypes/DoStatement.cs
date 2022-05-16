using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{

public class DoStatement : IStatement
{
    public IList<IStatement> condition = new List<IStatement>();

    public IList<IStatement> body = new List<IStatement>();

}

};