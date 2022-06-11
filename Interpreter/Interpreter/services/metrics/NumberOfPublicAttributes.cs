using System.Collections;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics{
    public static class NumberOfPublicAttributes
    {
        public static void CalculateNopa(ArrayList filler, ArrayList arrayList)
        {
            for (var i = 0; i < 3; i++)
            {
                ((MetricsModel) arrayList[i]).NOPA = ((MetricsAditionalData) filler[i]).numberOfPublicFields;
            } 
        }
    }
}