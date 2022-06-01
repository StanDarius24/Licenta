namespace Interpreter.Models.serialize.statementTypes{

    public class NamedTypeSpecifier: IStatement, INameInterface
    {
        public IStatement name { set; get; }
        public string GetWrittenName()
        {
            if (name is INameInterface)
            {
                return (name as INameInterface).GetWrittenName();
            }

            return "";
        }
    }

};