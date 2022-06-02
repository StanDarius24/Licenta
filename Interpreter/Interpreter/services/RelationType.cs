using System;
using System.Collections.Generic;
using System.Linq;
using Interpreter.Models.serialize;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;
using Interpreter.Utility;

namespace Interpreter.services{
    public class RelationType
    {
        public bool CheckForRelationType(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            if (CheckForClass(element, translation)) return false;
            if (CheckForLinkageSpecification(element, translation)) return false;
            return CheckForNameSpace(element, translation);
        }
        
        private bool CheckForClass(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            return element.classOrHeader.classList != null && (from classList in element.classOrHeader.classList where classList.our_class.baseSpecifier != null from baseSpec in classList.our_class.baseSpecifier select (baseSpec as BaseSpecifier).name.Equals(StringService.FixPath(translation.path))).FirstOrDefault();
        }
        
        private bool CheckForLinkageSpecification(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            if (element.path.Contains("LinkageImplementation"))
            {
                Console.Out.Write("test"); 
            }
            return false;
        }

        public static IList<string> GetParentClassNameList(IEnumerable<ComplexCompositeTypeSpecifier> listOfClasses)
        {
            var listToReturn = new List<string>();
            foreach (var classToTest in listOfClasses)
            {
                listToReturn.AddRange(
                    classToTest.our_class.baseSpecifier.Select(
                        superClass => ((superClass as BaseSpecifier)?.name as INameInterface)?.GetWrittenName()));
            }
            return listToReturn;
        }
        
        private bool CheckForNameSpace(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            if (translation.classOrHeader.namespaces.Count <= 0) return false;
            if (element.classOrHeader.classList.Count <= 0) return false;
            foreach (NameSpace nameSpaceElement in translation.classOrHeader.namespaces)
            foreach (var nameSpaceDecl in nameSpaceElement.declarations)
            {
                if (nameSpaceDecl is ComplexCompositeTypeSpecifier)
                    foreach (var classListElement in element.classOrHeader.classList)
                    {
                        foreach (var classBaseSpecifierList in classListElement.our_class.baseSpecifier)
                        {
                            if (((INameInterface) (classBaseSpecifierList as BaseSpecifier)?.name)!.GetWrittenName()
                                .Equals(((nameSpaceDecl as ComplexCompositeTypeSpecifier).our_class.name as INameInterface)?.GetWrittenName()))
                                return true;
                        }
                    }
                if ((nameSpaceDecl as SimpleDeclaration)?.declSpecifier is not ElaboratedTypeSpecifier) continue;
                if (GetParentClassNameList(element.classOrHeader.classList).Contains(
                        ((nameSpaceDecl as SimpleDeclaration).declSpecifier as INameInterface)?.GetWrittenName()))
                {
                    return true;
                }
            }
            return false;
        }
    }
};