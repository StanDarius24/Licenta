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
            if (CheckExternDeclaration(element, translation)) return true;
            if (CheckInternDeclaration(element, translation)) return true;
            if (CheckExternal(element, translation)) return true;
            return CheckFunctionsParametersWithoutImpr(element, translation);
        }

        private bool CheckExternal(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            var numberOfExternals = 0;
            if (element.classOrHeader.externalMethods.Count > 0 && translation.classOrHeader.externalMethods.Count > 0)
            {
                numberOfExternals += element.classOrHeader.externalMethods.Count(elementExternal => translation.classOrHeader.externalMethods.Contains(elementExternal));
            }
            return numberOfExternals == element.classOrHeader.externalMethods.Count;
        }

        private bool CheckFunctionsParametersWithoutImpr(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            foreach (var functionDeclarator in element.classOrHeader.methodsWithFunctionCalls)
            {
                foreach (var functionParameter in (functionDeclarator.declarator[0] as FunctionDeclarator)?.parameter!)
                {
                    if (functionParameter is ParameterDeclaration declaration)
                    {
                        if (GetClassesNamesFromRelations(translation.classOrHeader.classList).Contains(
                                (declaration.declarationSpecifier as INameInterface)
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
            if (element.classOrHeader.internDeclaration != null && element.classOrHeader.internDeclaration.Count != 0)
            {
                Console.Out.Write("test");
                foreach (var internDecl in element.classOrHeader.internDeclaration)
                {
                    if (internDecl != null)
                    {
                        Console.Out.Write("test");

                    }

                    return true;
                }
            }
            return false;
        }

        private bool CheckExternDeclaration(ClassOrHeaderWithPath element, ClassOrHeaderWithPath translation)
        {
            return element.classOrHeader.globalDeclaration.Where(
                    globalDecl => translation.classOrHeader.classList.Count > 0)
                .Any(globalDecl => GetClassesNamesFromRelations(translation.classOrHeader.classList)
                    .Contains((globalDecl.declaration.declSpecifier as INameInterface)?.GetWrittenName())) || CheckExternDefinition(translation);
        }

        private bool CheckExternDefinition(ClassOrHeaderWithPath translation)
        {
            return translation.classOrHeader.globalDeclaration.Any(declaration => StringService.FixPath(translation.path)
                .Equals((declaration.declaration.declSpecifier as INameInterface)?.GetWrittenName()));
        }

        public static IList<string> GetClassesNamesFromRelations(IEnumerable<ComplexCompositeTypeSpecifier> classList)
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
                    foreach (var translationClassElement in translation.classOrHeader.classList)
                    {
                        if (declaration.declSpecifier is SimpleDeclaration)
                        {
                            if (((INameInterface) declaration.declSpecifier).GetWrittenName()
                                .Equals((translationClassElement.our_class.name as INameInterface)?.GetWrittenName()))
                            {
                                return true;
                            }
                        } else if (declaration.declSpecifier is INameInterface @interface)
                        {
                            if (@interface.GetWrittenName()
                                .Equals((translationClassElement.our_class.name as INameInterface)?.GetWrittenName()))
                            {
                                return true;
                            }
                        }
                    }
                }

            }
            return false;
        }
    }
};