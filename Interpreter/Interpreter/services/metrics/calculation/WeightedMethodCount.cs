using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics.calculation{
    public static class WeightedMethodCount
    {
        public static void CalculateWmc(MetricsInFile filler)
        {
            CalculateExternalClass(filler.ExternalClasses);
            CalculateClasses(filler.classMetrics);
            CalculateNameSpec(filler.nameSpaceMetrics);
        }

        private static void CalculateNameSpec(IList<MetricsInFile> fillerNameSpaceMetrics)
        {
            if (fillerNameSpaceMetrics != null)
            {
                foreach (var fillerNameSpaceMetric in fillerNameSpaceMetrics)
                {
                    CalculateExternalClass(fillerNameSpaceMetric.ExternalClasses);
                    CalculateClasses(fillerNameSpaceMetric.classMetrics);
                }
            }
        }

        private static void CalculateClasses(IList<MetricsAditionalData> fillerClassMetrics)
        {
            if (fillerClassMetrics != null)
            {
                foreach (var classMetric in fillerClassMetrics)
                {
                    classMetric.metricsModel.WMC = classMetric.totalComplexity;
                }
            }
        }

        private static void CalculateExternalClass(MetricsAditionalData fillerExternalClasses)
        {
            if (fillerExternalClasses != null)
            {
                fillerExternalClasses.metricsModel.WMC = fillerExternalClasses.totalComplexity;
            }
        }
        
    }
}