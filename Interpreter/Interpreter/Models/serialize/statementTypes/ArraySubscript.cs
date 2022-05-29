namespace Interpreter.Models.serialize.statementTypes{
    public class ArraySubscript: IStatement, INameInterface
    {
        public string arrayValue { get; set; }
    
        public string index { get; set; }
        public string GetWrittenName()
        {
            return arrayValue;
        }
    }
};