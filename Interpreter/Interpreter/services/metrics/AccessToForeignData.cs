using System.Collections;
using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics{
    public static class AccessToForeignData
    {
        public static void CalculateAtfd(MetricsInFile filler)
        {
            CalculateExtern(filler.ExternalClasses);
            CalculateClass(filler.classMetrics);
            CalculateNameSpace(filler.nameSpaceMetrics);
        }

        private static void CalculateNameSpace(IList<MetricsInFile> fillerNameSpaceMetrics)
        {
            if (fillerNameSpaceMetrics == null) return;
            foreach (var fillerNameSpaceMetric in fillerNameSpaceMetrics)
            {
                CalculateExtern(fillerNameSpaceMetric.ExternalClasses);
                CalculateClass(fillerNameSpaceMetric.classMetrics);
            }
        }

        private static void CalculateClass(IList<MetricsAditionalData> fillerClassMetrics)
        {
            if (fillerClassMetrics == null) return;
            foreach (var fillerClassMetric in fillerClassMetrics)
            {
                fillerClassMetric.metricsModel.ATFD = fillerClassMetric.numberOfAccessedAttributes;
            }
        }

        private static void CalculateExtern(MetricsAditionalData fillerExternalClasses)
        {
            if (fillerExternalClasses != null)
            {
                fillerExternalClasses.metricsModel.ATFD = fillerExternalClasses.numberOfAccessedAttributes;
            }
        }
    }
}