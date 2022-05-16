namespace Interpreter.Models.serialize.statementTypes{
    public class While: IStatement
    {
        public IStatement condition { set; get; }
        
        public IStatement condition2 { set; get; }

        public IStatement body { set; get; }

    }
};