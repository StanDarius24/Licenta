using Interpreter.Models.metrics;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.services.metrics{
    public class ExternMetrics
    {
        public static void CalculateExternMethodAndComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsInFile filler)
        {
            if (filler.ExternalClasses == null)
            {
                filler.ExternalClasses = new MetricsAditionalData();
            }
            foreach (var functionDeclarator in classOrHeaderWithPath.classOrHeader.methodsWithFunctionCalls)
            {
                if ((functionDeclarator.declarator[0] as FunctionDeclarator)?.name is Name)
                {
                    filler.ExternalClasses.numberOfMethods++;
                    filler.ExternalClasses.totalComplexity += functionDeclarator.cyclomaticComplexity;
                }
            }
        }
    }
};