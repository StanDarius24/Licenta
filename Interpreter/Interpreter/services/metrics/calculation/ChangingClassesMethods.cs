using Interpreter.Models.metrics;

namespace Interpreter.services.metrics.calculation{
    public class ChangingClassesMethods
    {
        public static void CalculateCcCm(MetricsInFile filler)
        {
            CalculateExtern(filler);
            CalculateClass(filler);
            // CalculateNameSpace(filler); //TODO
        }

        private static void CalculateClass(MetricsInFile filler)
        {
            foreach (var tuple in filler.ExternalClasses.numberOfClassesThatCallsMethodX)
            {
                if (tuple.Item1.Contains(" "))
                {
                    filler.ExternalClasses.metricsModel.CC++;
                }
                filler.ExternalClasses.metricsModel.CM++;
            }
            
        }

        private static void CalculateExtern(MetricsInFile filler)
        {
            if (filler.classMetrics != null)
            {
                foreach (var metric in filler.classMetrics)
                {
                    foreach (var tuple in metric.numberOfClassesThatCallsMethodX)
                    {
                        if (tuple.Item1.Contains(" "))
                        {
                            metric.metricsModel.CC++;
                        }
                        metric.metricsModel.CM++;
                    }
                }
            }
        }
    }
}