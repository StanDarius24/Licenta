namespace Interpreter.Models{

    public class TemplateSpecialization : IStatement
    {
        public IStatement declaration { set; get; }
    }

}