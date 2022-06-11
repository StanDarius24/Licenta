using System.Collections;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics{
    public static class WeightedMethodCount
    {
        public static void CalculateWmc(ArrayList filler, ArrayList arrayList)
        {
            for (var i = 0; i < 3; i++)
            {
                ((MetricsModel) arrayList[i]).WMC = ((MetricsAditionalData) filler[i]).totalComplexity;
            }
        }
    }
}