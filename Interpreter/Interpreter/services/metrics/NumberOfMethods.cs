using System.Collections;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics{
    public static class NumberOfMethods
    {
        public static void CalculateNom(ArrayList filler, ArrayList arrayList)
        {
            for (var i = 0; i < 3; i++)
            {
                ((MetricsModel) arrayList[i]).NOM = ((MetricsAditionalData) filler[i]).numberOfMethods;
            }       
        }
    }
}