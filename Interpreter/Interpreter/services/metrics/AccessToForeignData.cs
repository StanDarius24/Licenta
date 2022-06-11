using System.Collections;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics{
    public static class AccessToForeignData
    {
        public static void CalculateAtfd(ArrayList filler, ArrayList arrayList)
        {
            for (var i = 0; i < 3; i++)
            {
                // ((MetricsModel) arrayList[i]).AMW = ((MetricsAditionalData) filler[i]).
            }
        }
    }
}