using System;
using System.Collections.Generic;
using System.Linq;
using Interpreter.Models.serialize;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;
using Interpreter.Utility;
using Microsoft.Win32;
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
                    var translation = LookUp4HeaderImplementation(StringService.FixHeaderName(headerFileName), vcxprojStructure);
                    if (translation == null) continue;
                    if (StringService.FixPath(StringService.FixHeaderName(headerFileName)).Equals(StringService.FixPath(element.path)))
                    {
                        if (!StringService.GetDirectoryPath(element.path).Equals(StringService.GetDirectoryPath(translation.path))) continue;
                        element.AsociatedFile = translation;
                        translation.AsociatedFile = element;
                    }
                    else
                    {
                        if (CheckForRelationType(element, translation))
                        {
                            element.ListOfInheritance.Add(translation);
                        } else if (CheckDeclaration(element, translation))
                        {
                            element.ListOfDeclaration.Add(translation);
                        }
                    }
                }
            }
        }

        private static bool CheckDeclaration(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            throw new NotImplementedException();
        }

        private static bool CheckForRelationType(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            if (!CheckForClass(element, translation))
            {
                if (!CheckForLinkageSpecification(element, translation))
                {
                    if (!CheckForNameSpace(element, translation))
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        private static bool CheckForNameSpace(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            if (translation.classOrHeader.namespaces.Count > 0)
            {
                if (element.classOrHeader.classList.Count > 0)
                {
                    foreach (var nameSpaceElement in translation.classOrHeader.namespaces)
                    {
                        foreach (var nameSpaceDecl in nameSpaceElement.declarations)
                        {
                            if (nameSpaceDecl is ComplexCompositeTypeSpecifier)
                            {
                                foreach (var classListElement in element.classOrHeader.classList)
                                {
                                    foreach (var classBaseSpecifierList in classListElement.our_class.baseSpecifier)
                                    {
                                        Console.Out.Write("");
                                        if (((INameInterface) (classBaseSpecifierList as BaseSpecifier)?.name)!
                                            .GetWrittenName().Equals(
                                                ((nameSpaceDecl as ComplexCompositeTypeSpecifier).our_class
                                                    .name as INameInterface)?.GetWrittenName()))
                                        {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }

        private static bool CheckForLinkageSpecification(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            if (element.path.Contains("LinkageImplementation"))
            {
                Console.Out.Write("test"); // need fix here
            }
            return false;
        }

        private static bool CheckForClass(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            if (element.classOrHeader.classList != null)
            {
                foreach (var classList in element.classOrHeader.classList)
                {
                    if (classList.our_class.baseSpecifier != null)
                    {
                        foreach (var baseSpec in classList.our_class.baseSpecifier)
                        {
                            return (baseSpec as BaseSpecifier).name.Equals(
                                StringService.FixPath(translation.path));
                        }
                    }
                }
            }

            return false;
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
            return _listOfIncludedLivraries.Contains(StringService.DeleteExtension(headerFileName));
        }

    }
}