namespace Interpreter.Models.serialize.statementTypes{
    public class SimpleDeclSpecifier : IStatement
    {
        public string declarationSpecifier { set; get; }
        public int type { set; get; }
        public bool isSigned { set; get; }
        public bool isUnsigned { set; get; }
        public bool isShort { set; get; }
        public bool isLong { set; get; }
        public bool isLongLong { set; get; }
        public bool isComplex { set; get; }
        public bool isImaginary { set; get; }
        public IStatement fDeclTypeExpression { set; get; }
        public bool isConstant { set; get; }
        public bool isExplicit { set; get; }
        public bool isFriend { set; get; } 
        public bool isInline { set; get; }
        public bool isRestrict { set; get; }
        public bool isThreadLocal { set; get; }
        public bool isVirtual { set; get; }
        public bool isVolatile { set; get; }
    }
}