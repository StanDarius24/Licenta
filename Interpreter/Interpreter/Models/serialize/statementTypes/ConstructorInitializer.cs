using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{

public class ConstructorInitializer: IStatement
{
    public IList<IStatement> expressions = new List<IStatement>();
}

};