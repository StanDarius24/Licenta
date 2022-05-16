namespace Interpreter.Models.serialize.statementTypes{
    public class ArraySubscript: IStatement
    {
        public string arrayValue { get; set; }
    
        public string index { get; set; }
    }
};