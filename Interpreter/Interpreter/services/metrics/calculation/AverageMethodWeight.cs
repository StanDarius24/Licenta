using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics.calculation{
    public static class AverageMethodWeight
    {
        public static void CalculateAmw(MetricsInFile filler)
        {
            CalculateExtern(filler.ExternalClasses);
            CalculateClasses(filler.classMetrics);
            CalculateNameSpace(filler.nameSpaceMetrics);
        }

        private static void CalculateNameSpace(IEnumerable<MetricsInFile> fillerNameSpaceMetrics)
        {
            if (fillerNameSpaceMetrics == null) return;
            foreach (var nameSpaceMetric in fillerNameSpaceMetrics)
            {
                if (nameSpaceMetric.ExternalClasses != null)
                {
                    CalculateExtern(nameSpaceMetric.ExternalClasses);
                }
                if (nameSpaceMetric.classMetrics is {Count: > 0})
                {
                   CalculateClasses(nameSpaceMetric.classMetrics);
                }
                if (nameSpaceMetric.nameSpaceMetrics is {Count: > 0})
                {
                    CalculateNameSpace(nameSpaceMetric.nameSpaceMetrics);
                }
            }
        }

        private static void CalculateClasses(IEnumerable<MetricsAditionalData> fillerClassMetrics)
        {
            if (fillerClassMetrics == null) return;
            foreach (var dataInClass in fillerClassMetrics)
            {
                dataInClass.metricsModel.AMW = dataInClass.totalComplexity / dataInClass.numberOfMethods;
            }
        }

        private static void CalculateExtern(MetricsAditionalData fillerExternalClasses)
        {
            if (fillerExternalClasses != null)
            {
                fillerExternalClasses.metricsModel.AMW =
                    fillerExternalClasses.totalComplexity / fillerExternalClasses.numberOfMethods;
            }
        }
    }
}