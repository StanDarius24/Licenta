using System;
using System.Collections.Generic;
using System.Linq;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;
using Interpreter.Utility;

namespace Interpreter.services{
    public class DeclarationType
    {
        public bool CheckDeclaration(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            if (CheckFoClass(element, translation)) return true;
            if (!CheckExternDeclaration(element, translation))
            {
                if (!CheckInternDeclaration(element, translation))
                {
                    if (!CheckFunctionsParametersWithoutImpr(element, translation))
                    {
                        Console.Out.Write("test");
                    }
                } 
            }
            return true;
        }

        private bool CheckFunctionsParametersWithoutImpr(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            foreach (var functionDeclarator in element.classOrHeader.functionCallsWithoutImplementation)
            {
                foreach (var functionParameter in functionDeclarator.parameter)
                {
                    if (functionParameter is ParameterDeclaration)
                    {
                        if (GetClassesNamesFromRelations(translation.classOrHeader.classList).Contains(
                                ((functionParameter as ParameterDeclaration).declarationSpecifier as INameInterface)
                                ?.GetWrittenName()))
                        {
                            return true;
                        }
                    }
                    else
                    {
                        throw new Exception("param problem");
                    }
                }
            }
            return false;
        }

        private bool CheckInternDeclaration(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            return element.classOrHeader.internDeclaration.Any(internDecl => GetClassesNamesFromRelations(translation.classOrHeader.classList)
                .Contains((internDecl.declaration.declSpecifier as INameInterface)?.GetWrittenName()));
        }

        private bool CheckExternDeclaration(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            foreach (var globalDecl in element.classOrHeader.globalDeclaration)
            {
                if (translation.classOrHeader.classList.Count > 0)
                {
                    if (GetClassesNamesFromRelations(translation.classOrHeader.classList)
                        .Contains((globalDecl.declaration.declSpecifier as INameInterface)?.GetWrittenName()))
                    {
                        return true;
                    }
                }
            }
            
            return false;
        }

        private static IList<string> GetClassesNamesFromRelations(IEnumerable<ComplexCompositeTypeSpecifier> classList)
        {
            return classList.Select(classElement => (classElement.our_class.name as INameInterface)?.GetWrittenName()).ToList();
        }

        private bool CheckFoClass(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            foreach (var classElement in element.classOrHeader.classList)
            {
                foreach (var declarationElement in classElement.our_class.declarations)
                {
                    if (declarationElement is not SimpleDeclaration declaration) continue;
                    if (translation.classOrHeader.classList.Any(
                            translationClassElement => ((INameInterface) declaration.declSpecifier).GetWrittenName()
                            .Equals((translationClassElement.our_class.name as INameInterface)?.GetWrittenName())))
                    {
                        return true;
                    }
                }

            }
            return false;
        }
    }
};