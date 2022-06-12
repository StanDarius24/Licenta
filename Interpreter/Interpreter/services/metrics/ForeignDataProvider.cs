using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics;

public class ForeignDataProvider
{
    public static void CalculateFdp(MetricsInFile filler)
    {
        CalculateExtern(filler.ExternalClasses);
        CalculateClass(filler.classMetrics);
        CalculateNameSpace(filler.nameSpaceMetrics);
    }

    private static void CalculateNameSpace(IList<MetricsInFile> fillerNameSpaceMetrics)
    {
        throw new System.NotImplementedException();
    }

    private static void CalculateClass(IList<MetricsAditionalData> fillerClassMetrics)
    {
        throw new System.NotImplementedException();
    }

    private static void CalculateExtern(MetricsAditionalData fillerExternalClasses)
    {
        throw new System.NotImplementedException();
    }
}