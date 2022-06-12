using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics
{
    public class BaseClassOverridingRatio
    {
        public static void CalculateBOvR(MetricsInFile file)
        {
            CalculateExtern(file.ExternalClasses);
            CalculateClass(file.classMetrics);
            CalculateNameSpace(file.nameSpaceMetrics);
        }

        private static void CalculateNameSpace(IList<MetricsInFile> fileNameSpaceMetrics)
        {
            if (fileNameSpaceMetrics == null) return;
            foreach (var fileNameSpaceMetric in fileNameSpaceMetrics)
            {
                CalculateExtern(fileNameSpaceMetric.ExternalClasses);
                CalculateClass(fileNameSpaceMetric.classMetrics);
            }
        }

        private static void CalculateClass(IList<MetricsAditionalData> fileClassMetrics)
        {
            if (fileClassMetrics == null) return;
            foreach (var fileClassMetric in fileClassMetrics)
            {
                fileClassMetric.metricsModel.BOvR =
                    fileClassMetric.numberOfOverridingMethods / fileClassMetric.numberOfMethods;
            }
        }

        private static void CalculateExtern(MetricsAditionalData fileExternalClasses)
        {
            if (fileExternalClasses != null)
            {
                fileExternalClasses.metricsModel.BOvR =
                    fileExternalClasses.numberOfOverridingMethods / fileExternalClasses.numberOfMethods;
            }
        }
    }
}