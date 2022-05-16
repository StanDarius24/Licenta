namespace Interpreter.Models.statementTypes{

    public class AliasDeclaration : IStatement
    {
        public string aliasName { set; get; }
        
        public string mappingTypeId { set; get; }
    }

};