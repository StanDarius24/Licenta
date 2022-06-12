using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics{
    public static class NumberOfMethods
    {
        public static void CalculateNom(MetricsInFile filler)
        {
            CalculateMethods(filler.ExternalClasses);
            CalculateClass(filler.classMetrics);
            CalculateNameSpace(filler.nameSpaceMetrics);
        }

        private static void CalculateNameSpace(IList<MetricsInFile> fillerNameSpaceMetrics)
        {
            if (fillerNameSpaceMetrics == null) return;
            foreach (var nameSpaceMetric in fillerNameSpaceMetrics)
            {
                CalculateMethods(nameSpaceMetric.ExternalClasses);
                CalculateClass(nameSpaceMetric.classMetrics);
            }
        }

        private static void CalculateClass(IList<MetricsAditionalData> fillerClassMetrics)
        {
            if (fillerClassMetrics == null) return;
            foreach (var classMetric in fillerClassMetrics)
            {
                classMetric.metricsModel.NOM = classMetric.numberOfMethods;
            }
        }

        private static void CalculateMethods(MetricsAditionalData fillerExternalClasses)
        {
            if (fillerExternalClasses != null)
            {
                fillerExternalClasses.metricsModel.NOM = fillerExternalClasses.numberOfMethods;
            }
        }
    }
}