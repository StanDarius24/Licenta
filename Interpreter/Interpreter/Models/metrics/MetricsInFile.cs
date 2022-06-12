using System.Collections.Generic;

namespace Interpreter.Models.metrics{
    public class MetricsInFile
    {
        public IList<MetricsAditionalData> classMetrics = new List<MetricsAditionalData>();

        public IList<MetricsInFile> nameSpaceMetrics = new List<MetricsInFile>();
        
        public MetricsAditionalData ExternalClasses { set; get; }
    }
};