namespace Interpreter.Models{

    public class ParameterDeclaration: IStatement
    {
        public IStatement declarationSpecifier { set; get; }
        
        public IStatement declarator { set; get; }
        
    }

};