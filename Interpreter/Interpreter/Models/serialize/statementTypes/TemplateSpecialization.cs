namespace Interpreter.Models.statementTypes{

    public class TemplateSpecialization : IStatement
    {
        public IStatement declaration { set; get; }
    }

}