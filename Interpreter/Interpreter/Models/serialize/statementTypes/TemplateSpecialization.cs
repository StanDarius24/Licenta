namespace Interpreter.Models.serialize.statementTypes{

    public class TemplateSpecialization : IStatement
    {
        public IStatement declaration { set; get; }
    }

}