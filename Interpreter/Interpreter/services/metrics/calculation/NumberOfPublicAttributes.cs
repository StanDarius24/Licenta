using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics.calculation{
    public static class NumberOfPublicAttributes
    {
        public static void CalculateNopa(MetricsInFile filler)
        {
            CalculateMethod(filler.ExternalClasses);
            CalculateClass(filler.classMetrics);
            CalculateNameSpace(filler.nameSpaceMetrics);
        }

        private static void CalculateNameSpace(IList<MetricsInFile> fillerNameSpaceMetrics)
        {
            if (fillerNameSpaceMetrics == null) return;
            foreach (var fillerNameSpaceMetric in fillerNameSpaceMetrics)
            {
                CalculateMethod(fillerNameSpaceMetric.ExternalClasses);
                CalculateClass(fillerNameSpaceMetric.classMetrics);
            }
        }

        private static void CalculateClass(IList<MetricsAditionalData> fillerClassMetrics)
        {
            if (fillerClassMetrics == null) return;
            foreach (var fillerClassMetric in fillerClassMetrics)
            {
                fillerClassMetric.metricsModel.NOPA = fillerClassMetric.numberOfPublicFields;
            }
        }

        private static void CalculateMethod(MetricsAditionalData fillerExternalClasses)
        {
            if (fillerExternalClasses != null)
            {
                fillerExternalClasses.metricsModel.NOPA = fillerExternalClasses.numberOfPublicFields;
            }
        }
    }
}