using System;
using System.Collections.Generic;
using System.Linq;
using Interpreter.Models;
using Interpreter.Models.complexStatementTypes;

namespace Interpreter.services{
    public class DirectiveFinder
    {
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
                    LookUp4HeaderImplementation(FixHeaderName(headerFileName), vcxprojStructure);
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

        private static void LookUp4HeaderImplementation(string headerFileName, VcxprojStructure vcxprojStructure)
        {
            if (!vcxprojStructure.listOfHeaderFiles.Contains(headerFileName))
            {
                headerFileDifferentVcxproj(headerFileName);
            }
            else
            {
                Console.Out.Write("de");
            }
        }

        private static ComplexFinalTranslation headerFileDifferentVcxproj(string headerFileName)
        {
            return DataRegistry.deserializedData.FirstOrDefault(complexFinalTranslation => complexFinalTranslation.vcxprojStructure.listOfHeaderFiles.Contains(headerFileName));
        }
    }
};