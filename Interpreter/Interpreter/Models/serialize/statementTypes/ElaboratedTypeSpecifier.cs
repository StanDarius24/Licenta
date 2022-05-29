namespace Interpreter.Models.serialize.statementTypes{

    public class ElaboratedTypeSpecifier : IStatement, INameInterface
    {
        public string name { set; get; }
        public string GetWrittenName()
        {
            return name;
        }
    }

};