namespace Interpreter.Models.serialize.statementTypes{

    public class If : IStatement
    {
        public IStatement condition { set; get; }
        
        public IStatement thenClause { set; get; }

        public IStatement elseClause { set; get; }

        public IStatement condDecl { set; get; }
        
    }

};