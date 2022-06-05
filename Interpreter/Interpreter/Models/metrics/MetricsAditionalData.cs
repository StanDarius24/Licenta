namespace Interpreter.Models.metrics{
    public class MetricsAditionalData
    {
        public MetricsAditionalData()
        {
            numberOfConstructors = 0;
            numberOfMethods = 0;
            totalComplexity = 0;
            numberOfPublicFields = 0;
            
            numberOfContainedFields = 0;
            numberOfProtectedMethodsFields = 0;
        }
        
        public string path { set; get; }
        public float numberOfMethods { set; get; }
        public float numberOfConstructors { set; get; }
        public float numberOfPublicFields { set; get; }
        public float numberOfContainedFields { set; get; }
        public float numberOfProtectedMethodsFields { set; get; }
        public float totalComplexity { set; get; }
    }
};