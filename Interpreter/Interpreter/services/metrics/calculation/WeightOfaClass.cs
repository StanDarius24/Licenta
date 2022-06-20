using System;
using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics.calculation
{
    public static class WeightOfaClass
    {
        public static void CalculateWoc(MetricsInFile filler)
        {
            CalculateMethod(filler.ExternalClasses);
            CalculateClass(filler.classMetrics);
            CalculateNameSpace(filler.nameSpaceMetrics);
        }

        private static void CalculateNameSpace(IList<MetricsInFile> fillerNameSpaceMetrics)
        {
            if (fillerNameSpaceMetrics == null) return;
            foreach (var nameSpaceMetric in fillerNameSpaceMetrics)
            {
                CalculateMethod(nameSpaceMetric.ExternalClasses);
                CalculateClass(nameSpaceMetric.classMetrics);
            }
        }

        private static void CalculateClass(IList<MetricsAditionalData> fillerClassMetrics)
        {
            if (fillerClassMetrics == null) return;
            foreach (var fillerClassMetric in fillerClassMetrics)
            {
                fillerClassMetric.metricsModel.WOC =
                    (fillerClassMetric.numberOfMethods - fillerClassMetric.numberOfAbstractMethods) /
                    fillerClassMetric.numberOfPublicFields;
                if (double.IsNaN(fillerClassMetric.metricsModel.WOC))
                {
                    fillerClassMetric.metricsModel.WOC = 1;
                } else if (double.IsInfinity(fillerClassMetric.metricsModel.WOC))
                {
                    fillerClassMetric.metricsModel.WOC = 0;
                }
            }
        }

        private static void CalculateMethod(MetricsAditionalData fillerExternalClasses)
        {
            if (fillerExternalClasses != null)
            {
                fillerExternalClasses.metricsModel.WOC =
                    (fillerExternalClasses.numberOfMethods - fillerExternalClasses.numberOfAbstractMethods) /
                    fillerExternalClasses.numberOfPublicFields;
                if (double.IsNaN(fillerExternalClasses.metricsModel.WOC))
                {
                    fillerExternalClasses.metricsModel.WOC = 1;
                }else if (double.IsInfinity(fillerExternalClasses.metricsModel.WOC))
                {
                    fillerExternalClasses.metricsModel.WOC = 0;
                }
            }
        }
    }
}