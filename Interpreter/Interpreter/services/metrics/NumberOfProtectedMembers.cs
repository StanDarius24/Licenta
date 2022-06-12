using System.Collections;
using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics{
    public static class NumberOfProtectedMembers
    {
        public static void CalculateNopm(MetricsInFile filler)
        {
            CalculateExtern(filler.ExternalClasses);
            CalculateClass(filler.classMetrics);
            CalculateNamespace(filler.nameSpaceMetrics);
        }
        private static void CalculateNamespace(IList<MetricsInFile> fillerNameSpaceMetrics)
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
                fillerClassMetric.metricsModel.NProtM = fillerClassMetric.numberOfProtectedMethodsFields;
            }
        }
        private static void CalculateExtern(MetricsAditionalData fillerExternalClasses)
        {
            if (fillerExternalClasses != null)
            {
                fillerExternalClasses.metricsModel.NProtM = fillerExternalClasses.numberOfProtectedMethodsFields;
            }
        }
    }
}