using System.Collections.Generic;
using System.Linq;
using Interpreter.Models.serialize;
using Interpreter.Models.serialize.complexStatementTypes;
using OperatingSystem = Interpreter.Utility.OperatingSystem;

namespace Interpreter.services{
    
    public class DirectiveFinder
    {
        private static IList<string> _listOfIncludedLivraries = new List<string>(){ "any", "atomic", "chrono", "concepts", "expected", "functional", "memory", "memory_resource", "scoped_allocator", "stdexcept", "system_error", "optional", "stacktrace", "tuple", "type_traits", "utility", "variant", "compare", "coroutine", "exception","initializer_list", "limits", "new", "source_location", "typeinfo", "version", "array", "bitset", "deque", "forward_list", "list", "map", "queue", "set", "span", "stack", "unordered_map", "unordered_set", "vector", "algorithm", "execution", "iterator", "ranges","locale", "codecvt", "charconv", "format", "string", "string_view", "regex", "filesystem", "fstream", "iomanip", "ios", "iosfwd", "iostream", "istream", "ostream", "spanstream", "sstream", "streambuf", "syncstream", "barrier", "condition_variable", "future", "latch", "mutex", "shared_mutex", "semaphore", "stop_token", "thread", "bit", "complex", "numbers", "random", "ratio", "valarray", "numeric", "assert", "complex", "ctype", "errno", "fenv", "float", "inttypes", "iso646", "limits", "locale", "math", "setjmp", "signal", "stdalign", "stdarg", "stdatomic", "stdbool", "stddef", "stdint", "stdio", "stdlib", "stdnoreturn", "string", "tgmath", "threads", "time","uchar", "wchar", "wctype"};
        
        
        
        public static void LinkDirective()
        {
            foreach (var complexFinalTranslation in DataRegistry.deserializedData)
            {
                ParseTranslationWithPath(complexFinalTranslation.listOfCppFiles, complexFinalTranslation.vcxprojStructure);
                ParseTranslationWithPath(complexFinalTranslation.listOfHeaderFiles, complexFinalTranslation.vcxprojStructure);
            }
        }

        private static void ParseTranslationWithPath(IEnumerable<ClassOrHeaderWithPath> list, VcxprojStructure vcxprojStructure)
        {
            foreach (var element in list)
            {
                if (element.classOrHeader.directives == null) continue;
                foreach (var headerFileName in element.classOrHeader.directives)
                {
                    var translation = LookUp4HeaderImplementation(FixHeaderName(headerFileName), vcxprojStructure);
                    if (translation != null)
                    {
                        element.ListOfInheritance.Add(translation);
                    }
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
                string newReturn;
                newReturn = returnedHeaderName.Contains('>') ? returnedHeaderName.Substring(0, returnedHeaderName.IndexOf('>')) : returnedHeaderName;
                return newReturn.Contains("/") ? newReturn.Split('/')[1] : newReturn;
            }
        }

        private static ClassOrHeaderWithPath LookUp4HeaderImplementation(string headerFileName, VcxprojStructure vcxprojStructure)
        {
            if (!vcxprojStructure.listOfHeaderFiles.Contains(headerFileName))
            {
                if (!CheckForInternalLibraries(headerFileName))
                {
                    return GetFileFromDifferentTranslation(headerFileName);
                }
            }
            else
            {
                return FindFinalTranslation(headerFileName);
            }
            return null;
        }

        private static ClassOrHeaderWithPath FindFinalTranslation(string headerFile)
        {
            var vcxProj = HeaderFileDifferentVcxproj(headerFile);
            return vcxProj.listOfHeaderFiles.FirstOrDefault(internalHeaderFile => internalHeaderFile.path.Split(OperatingSystem.GetSeparator()).Last().Equals(headerFile));
        }

        private static RepositoryModel HeaderFileDifferentVcxproj(string headerFileName)
        {
            return DataRegistry.deserializedData.FirstOrDefault(complexFinalTranslation => complexFinalTranslation.vcxprojStructure.listOfHeaderFiles.Contains(headerFileName));
        }

        private static ClassOrHeaderWithPath GetFileFromDifferentTranslation(string headerFileName)
        {
            return DataRegistry.deserializedData.SelectMany(complexFinalTranslation => complexFinalTranslation.listOfHeaderFiles).FirstOrDefault(translationWithPath => translationWithPath.path.Split(OperatingSystem.GetSeparator()).Last().Equals(headerFileName));
        }

        private static bool CheckForInternalLibraries(string headerFileName)
        {
            return _listOfIncludedLivraries.Contains(DeleteExtension(headerFileName));
        }

        private static string DeleteExtension(string headerFileName)
        {
            return FixHeaderName(headerFileName).Split('.')[0];
        }
    }
};