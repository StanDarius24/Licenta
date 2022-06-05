using Interpreter.Models.metrics;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.services.metrics{
    public class ExternMetrics
    {
        public static void CalculateExternMethodAndComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsAditionalData filler)
        {
            foreach (var functionDeclarator in classOrHeaderWithPath.classOrHeader.functionCallsWithoutImplementation)
            {
                if (functionDeclarator.name is Name)
                {
                    filler.numberOfMethods++;
                    filler.totalComplexity += functionDeclarator.cyclomaticComplexity;
                }
            }
        }
    }
};