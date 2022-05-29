namespace Interpreter.Models.serialize.statementTypes
{

    public class Name : IStatement, INameInterface
    {
        public string name;
        public string GetWrittenName()
        {
            return name;
        }
    }

};