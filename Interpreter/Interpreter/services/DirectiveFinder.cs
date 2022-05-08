using System;
using System.Collections.Generic;
using System.Linq;
using Interpreter.Models;
using Interpreter.Models.complexStatementTypes;
using OperatingSystem = Interpreter.Utility.OperatingSystem;

namespace Interpreter.services{
    
    public class DirectiveFinder
    {
        private static IList<string> listOfIncludedLivraries = new List<string>(){ "any", "atomic", "chrono", "concepts", "expected", "functional", "memory", "memory_resource", "scoped_allocator", "stdexcept", "system_error", "optional", "stacktrace", "tuple", "type_traits", "utility", "variant", "compare", "coroutine", "exception","initializer_list", "limits", "new", "source_location", "typeinfo", "version", "array", "bitset", "deque", "forward_list", "list", "map", "queue", "set", "span", "stack", "unordered_map", "unordered_set", "vector", "algorithm", "execution", "iterator", "ranges","locale", "codecvt", "charconv", "format", "string", "string_view", "regex", "filesystem", "fstream", "iomanip", "ios", "iosfwd", "iostream", "istream", "ostream", "spanstream", "sstream", "streambuf", "syncstream", "barrier", "condition_variable", "future", "latch", "mutex", "shared_mutex", "semaphore", "stop_token", "thread", "bit", "complex", "numbers", "random", "ratio", "valarray", "numeric"};
        public static void LinkDirective()
        {
            foreach (var complexFinalTranslation in DataRegistry.deserializedData)
            {
                ParseTranslationWithPath(complexFinalTranslation.listOfCppFiles, complexFinalTranslation.vcxprojStructure);
                ParseTranslationWithPath(complexFinalTranslation.listOfHeaderFiles, complexFinalTranslation.vcxprojStructure);
            }
        }

        private static void ParseTranslationWithPath(IEnumerable<TranslationWithPath> list, VcxprojStructure vcxprojStructure)
        {
            foreach (var element in list)
            {
                if (element.finalTranslation.directives == null) continue;
                foreach (var headerFileName in element.finalTranslation.directives)
                {
                    var translation = LookUp4HeaderImplementation(FixHeaderName(headerFileName), vcxprojStructure);
                    if (translation != null)
                    {
                        element.relationList.Add(translation);
                    }
                    Console.Out.Write("test");
                }
            }
        }

        private static string FixHeaderName(string headerName)
        {
            var list = headerName.Split('\"');
            if (list.Length > 2)
            {
                return list[list.Length - 2];
            }
            else
            {
                var returnedHeaderName = headerName.Substring(headerName.IndexOf('<') + 1);
                return returnedHeaderName.Substring(0, returnedHeaderName.IndexOf('>'));
            }
        }

        private static TranslationWithPath LookUp4HeaderImplementation(string headerFileName, VcxprojStructure vcxprojStructure)
        {
            if (!vcxprojStructure.listOfHeaderFiles.Contains(headerFileName))
            {
                if (!CheckForInternalLibraries(headerFileName))
                {
                    var finalTranslationFile = HeaderFileDifferentVcxproj(headerFileName);
                    return null;
                }
            }
            else
            {
                return FindFinalTranslation(headerFileName);
            }

            return null;
        }

        private static TranslationWithPath FindFinalTranslation(string headerFile)
        {
            var vcxProj = HeaderFileDifferentVcxproj(headerFile);
            foreach (var internalHeaderFile in vcxProj.listOfHeaderFiles)
            {
                if (internalHeaderFile.path.Split(OperatingSystem.getSeparator()).Last().Equals(headerFile))
                {
                    return internalHeaderFile;
                }
            }
            return null;
        }

        private static ComplexFinalTranslation HeaderFileDifferentVcxproj(string headerFileName)
        {
            return DataRegistry.deserializedData.FirstOrDefault(complexFinalTranslation => complexFinalTranslation.vcxprojStructure.listOfHeaderFiles.Contains(headerFileName));
        }

        private static bool CheckForInternalLibraries(string headerFileName)
        {
            return listOfIncludedLivraries.Contains(headerFileName);
        }
    }
};