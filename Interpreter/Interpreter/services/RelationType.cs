using System;
using System.Linq;
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
        
        private bool CheckForNameSpace(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            if (translation.classOrHeader.namespaces.Count <= 0) return false;
            return element.classOrHeader.classList.Count > 0 && 
                   (from nameSpaceElement in translation.classOrHeader.namespaces
                       from nameSpaceDecl in nameSpaceElement.declarations
                       where nameSpaceDecl is ComplexCompositeTypeSpecifier 
                       from classListElement in element.classOrHeader.classList
                       from classBaseSpecifierList in classListElement.our_class.baseSpecifier
                       where ((INameInterface) (classBaseSpecifierList as BaseSpecifier)?.name)!.GetWrittenName()
                           .Equals(
                               ((nameSpaceDecl as ComplexCompositeTypeSpecifier).our_class.name as INameInterface)?.GetWrittenName()
                               ) 
                               select nameSpaceDecl).Any();
        }
    }
};