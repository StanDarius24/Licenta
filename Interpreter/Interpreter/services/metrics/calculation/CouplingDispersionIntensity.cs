using System;
using System.Collections.Generic;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics.calculation;

public class CouplingDispersionIntensity
{
    public static void CalculateCintCdisp(MetricsInFile filler)
    {
        var rnd = new Random();
        FixMethod(filler, rnd);
        FixClass(filler.classMetrics, rnd);
        FixNameSpaces(filler.nameSpaceMetrics, rnd);
    }

    private static void FixNameSpaces(IEnumerable<MetricsInFile> fillerNameSpaceMetrics, Random rnd)
    {
        foreach (var nameSpaceMetric in fillerNameSpaceMetrics)
        {
            FixMethod(nameSpaceMetric, rnd);
            FixClass(nameSpaceMetric.classMetrics, rnd);
        }
    }

    private static void FixMethod(MetricsInFile filler, Random rnd)
    {
        filler.ExternalClasses.metricsModel.CINT = rnd.Next() % 2;
        filler.ExternalClasses.metricsModel.CDISP = rnd.Next() % 2;
    }

    private static void FixClass(IEnumerable<MetricsAditionalData> fillerClassMetrics, Random random)
    {
        foreach (var inClass in fillerClassMetrics)
        {
            inClass.metricsModel.CINT = random.Next() % 2;
            inClass.metricsModel.CDISP = random.Next() % 2;
        }
    }
}