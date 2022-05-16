namespace Interpreter.Models.serialize.statementTypes{

    public class NamespaceAlias: IStatement
    {
        public IStatement alias { set; get; }
        
        public IStatement qualifiedName { set; get; }
        
    }

};