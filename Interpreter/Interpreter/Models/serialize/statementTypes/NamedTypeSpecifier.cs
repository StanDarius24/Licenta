namespace Interpreter.Models.serialize.statementTypes{

    public class NamedTypeSpecifier: IStatement
    {
        public IStatement name { set; get; }
    }

};