namespace Interpreter.Models.statementTypes{
    public class TypeId: IStatement
    {
        public IStatement declSpecifier { set; get; }
        
        public IStatement abstractDeclaration { set; get; }
    }
};