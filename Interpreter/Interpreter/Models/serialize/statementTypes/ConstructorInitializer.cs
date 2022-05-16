using System.Collections.Generic;

namespace Interpreter.Models.statementTypes{

public class ConstructorInitializer: IStatement
{
    public IList<IStatement> expressions = new List<IStatement>();
}

};