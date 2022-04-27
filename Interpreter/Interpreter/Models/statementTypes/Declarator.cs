namespace Interpreter.Models{

public class Declarator: IStatement
{
    public string modifier { set; get; }
    
    public string name { set; get; }

    public IStatement initialization { set; get; }

}

};