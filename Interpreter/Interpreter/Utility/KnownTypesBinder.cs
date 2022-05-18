using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using Newtonsoft.Json.Serialization;

namespace Interpreter.Utility{
    public class KnownTypesBinder : ISerializationBinder
    {
        public ICollection<string> knownType = new Collection<string>();
        public IList<Type> knownTypes { get; set; }

        public Type BindToType(string assemblyName, string typeName)
        {
            return knownTypes.SingleOrDefault(t => t.Name == typeName);
        }

        public void BindToName(Type serializedType, out string assemblyName, out string typeName)
        {
            assemblyName = null;
            typeName = serializedType.Name;
            knownType.Add(typeName);
        }
    }
};