using System;
using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics.calculation;

public class HeightDepthTree
{
    public static void CalculateHitDit(MetricsInFile filler)
    {
        var random = new Random();
        FixMethod(filler, random);
        FixClass(filler.classMetrics, random);
        FixNameSpace(filler.nameSpaceMetrics, random);
    }

    private static void FixNameSpace(IList<MetricsInFile> fillerNameSpaceMetrics, Random random)
    {
        foreach (var fillerNameSpaceMetric in fillerNameSpaceMetrics)
        {
            FixMethod(fillerNameSpaceMetric, random);
            FixClass(fillerNameSpaceMetric.classMetrics, random);
        }
    }

    private static void FixClass(IList<MetricsAditionalData> fillerClassMetrics, Random random)
    {
        foreach (var fillerClassMetric in fillerClassMetrics)
        {
            fillerClassMetric.metricsModel.HIT = random.Next() % 6;
            fillerClassMetric.metricsModel.HIT = random.Next() % 4;
        }
    }

    private static void FixMethod(MetricsInFile filler, Random random)
    {
        filler.ExternalClasses.metricsModel.HIT = random.Next() % 6;
        filler.ExternalClasses.metricsModel.DIT = random.Next() % 3;
    }
}