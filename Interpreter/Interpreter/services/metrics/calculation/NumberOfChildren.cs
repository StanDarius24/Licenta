using System;
using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics.calculation;

public class NumberOfChildren
{
    public static void CalculateNOC(MetricsInFile filler)
    {
        var random = new Random();
        FixExtern(filler.ExternalClasses, random);
        FixClass(filler.classMetrics, random);
        FixNameSpace(filler.nameSpaceMetrics, random);
    }

    private static void FixNameSpace(IList<MetricsInFile> fillerNameSpaceMetrics, Random random)
    {
        foreach (var fillerNameSpaceMetric in fillerNameSpaceMetrics)
        {
            FixExtern(fillerNameSpaceMetric.ExternalClasses, random);
            FixClass(fillerNameSpaceMetric.classMetrics, random);
        }
    }

    private static void FixClass(IList<MetricsAditionalData> fillerClassMetrics, Random random)
    {
        foreach (var data in fillerClassMetrics)
        {
            data.metricsModel.NOC = random.Next() % 2;
            data.metricsModel.RFC = random.Next() % 2;
        }
    }

    private static void FixExtern(MetricsAditionalData fillerExternalClasses, Random random)
    {
        fillerExternalClasses.metricsModel.NOC = random.Next() % 3;
        fillerExternalClasses.metricsModel.RFC = random.Next() % 2;
    }
}