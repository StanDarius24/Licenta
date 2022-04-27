using System.Collections.Generic;

namespace Interpreter.Models{

public class ConstructorInitializer: IStatement
{
    public IList<IStatement> expressions = new List<IStatement>();
}

};