using System;
using System.Collections.Generic;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.Models.metrics{
    public class MetricsAditionalData
    {
        public MetricsAditionalData(string name, string path)
        {            
            totalComplexity = 0;
            numberOfMethods = 0;
            numberOfPublicFields = 0;
            numberOfProtectedFields = 0;
            numberOfProtectedMethods = 0;
            numberOfFieldReferenceFromClass = 0;
            numberOfOverrideMethods = 0;
            numberOfAbstractMethods = 0;
            numberOfMethodCalls = 0;
            numberOfConstructors = 0;
            numberOfFieldsFromSuperClass = 0;
            numberOfClassesFieldReference = 0;
            metricsModel = new MetricsModel(name, path);
        }

        public MetricsModel metricsModel;

        public List<Tuple<string, string, int>>
            numberOfClassesThatCallsMethodX = new List<Tuple<string, string, int>>();
        public float numberOfFieldsFromSuperClass { set; get; }
        
        public float numberOfOverrideMethods { set; get; }
        public float numberOfFieldReferenceFromClass { set; get; }
        public float numberOfProtectedMethods { set; get; }
        public float numberOfProtectedFields { set; get; }
        public float numberOfOverridingMethods { set; get; }
        public float numberOfClassesFieldReference { set; get; }
        public float numberOfMethods { set; get; }
        public float numberOfConstructors { set; get; }
        public float numberOfPublicFields { set; get; }
        public float numberOfMethodCalls { set; get; }
        public float totalComplexity { set; get; }
        public float numberOfAbstractMethods { set; get; }
    }
};