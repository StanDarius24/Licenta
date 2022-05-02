namespace Interpreter.Models.statementTypes{

    public class NamedTypeSpecifier: IStatement
    {
        public IStatement name { set; get; }
    }

};