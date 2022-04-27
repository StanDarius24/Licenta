namespace Interpreter.Models{
    public class TypeId: IStatement
    {
        public IStatement declSpecifier { set; get; }
        
        public IStatement abstractDeclaration { set; get; }
    }
};