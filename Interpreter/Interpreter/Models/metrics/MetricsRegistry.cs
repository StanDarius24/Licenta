using System.Collections.Generic;

namespace Interpreter.Models.metrics{
    public static class MetricsRegistry
    {
        public static IList<MetricsInFile> metricsList = new List<MetricsInFile>();
    }
}