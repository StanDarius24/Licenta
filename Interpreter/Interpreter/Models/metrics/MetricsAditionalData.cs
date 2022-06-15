using System;
using System.Collections.Generic;

namespace Interpreter.Models.metrics{
    public class MetricsAditionalData
    {
        public MetricsAditionalData(string name, string path)
        {
            numberOfConstructors = 0;
            numberOfMethods = 0;
            totalComplexity = 0;
            numberOfPublicFields = 0;
            numberOfAbstractMethods = 0;
            numberOfContainedFields = 0;
            numberOfProtectedMethodsFields = 0;
            numberOfAccessedAttributes = 0;
            numberOfattributesDifferentClass = 0;
            metricsModel = new MetricsModel(name, path);
        }

        public MetricsModel metricsModel; 
       

        public List<Tuple<string, string, int>>
            numberOfClassesThatCallsMethodX = new List<Tuple<string, string, int>>();
        public float numberOfOverridingMethods { set; get; }
        public float numberOfattributesDifferentClass { set; get; }
        public float numberOfAccessedAttributes { set; get; }
        public float numberOfMethods { set; get; }
        public float numberOfConstructors { set; get; }
        public float numberOfPublicFields { set; get; }
        public float numberOfContainedFields { set; get; }
        public float numberOfProtectedMethodsFields { set; get; }
        public float totalComplexity { set; get; }
        public float numberOfAbstractMethods { set; get; }
    }
};