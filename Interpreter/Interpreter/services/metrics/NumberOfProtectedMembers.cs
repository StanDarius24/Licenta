using System.Collections;
using Interpreter.Models.metrics;

namespace Interpreter.services.metrics{
    public static class NumberOfProtectedMembers
    {
        public static void CalculateNopm(ArrayList filler, ArrayList arrayList)
        {
            for (var i = 0; i < 3; i++)
            {
                ((MetricsModel) arrayList[i]).NProtM =
                    ((MetricsAditionalData) filler[i]).numberOfProtectedMethodsFields;
            }      
        }
    }
}