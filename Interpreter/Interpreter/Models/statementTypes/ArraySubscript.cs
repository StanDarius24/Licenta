namespace Interpreter.Models{
    public class ArraySubscript: IStatement
    {
        public string arrayValue { get; set; }
    
        public string index { get; set; }
    }
};