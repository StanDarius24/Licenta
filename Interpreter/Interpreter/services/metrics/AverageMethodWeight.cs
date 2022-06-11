using System;
using System.Collections;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics{
    public static class AverageMethodWeight
    {
        public static void CalculateAmw(ArrayList filler, ArrayList arrayList)
        {
            for (var i = 0; i < 3; i++)
            {
                ((MetricsModel) arrayList[i]).AMW = ((MetricsAditionalData) filler[i]).totalComplexity /
                                                    ((MetricsAditionalData) filler[i]).numberOfMethods;
            }
        }
    }
}