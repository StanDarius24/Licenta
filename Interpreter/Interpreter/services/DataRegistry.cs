using System.Collections.Generic;
using Interpreter.Models;
using Interpreter.Models.serialize;

namespace Interpreter.services{
    public static class DataRegistry
    {
        public static IList<RepositoryModel> deserializedData = new List<RepositoryModel>();

    }
};