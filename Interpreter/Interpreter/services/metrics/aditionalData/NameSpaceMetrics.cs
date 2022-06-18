using System;
using System.Collections.Generic;
using Interpreter.Models.metrics;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.services.metrics.aditionalData
{
    public class NameSpaceMetrics
    {
        public static void CalculateNameSpaceMethodsComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsInFile filler)
        {
            var newFiller = new MetricsInFile();
            newFiller.nameSpaceMetrics = new List<MetricsInFile>();
            newFiller.ExternalClasses = new MetricsAditionalData("extern", classOrHeaderWithPath.path);
            foreach (var nameSpace in classOrHeaderWithPath.classOrHeader.namespaces)
            {
                foreach (var declaration in nameSpace.declarations)
                {
                    switch ((declaration as SimpleDeclaration)?.declSpecifier)
                    {
                        case CompositeTypeSpecifier:
                            var classInterior = new MetricsAditionalData(nameSpace.name + ":" + (((declaration as SimpleDeclaration).declSpecifier as CompositeTypeSpecifier)?.name as INameInterface)?.GetWrittenName(), classOrHeaderWithPath.path);
                            ClassMethodComplexity.CalculateClassMatrics(
                                (declaration as SimpleDeclaration).declSpecifier as CompositeTypeSpecifier,
                                classInterior, classOrHeaderWithPath, nameSpace);
                            newFiller.classMetrics.Add(classInterior);
                            break;
                        case SimpleDeclSpecifier:
                        {
                            Console.WriteLine("test");
                            // ClassMethodComplexity.CheckModifier(declaration as SimpleDeclaration, filler);
                            // foreach (var functionImplementation in classOrHeaderWithPath.classOrHeader
                            //              .methodsWithFunctionCalls)
                            // {
                            //     if (((INameInterface) (functionImplementation.declarator[0] as FunctionDeclarator)?.name)!.GetWrittenName()
                            //         .Equals(
                            //             ((INameInterface)
                            //                 ((declaration as SimpleDeclaration).declarators[0] as FunctionDeclarator)!
                            //                 .name).GetWrittenName()))
                            //     {
                            //         filler.totalComplexity += functionImplementation.cyclomaticComplexity;
                            //         filler.numberOfMethods++;
                            //     }
                            // }
                            //
                            break;
                        }
                        default:
                        {
                            if (declaration is FunctionDefinition definition)
                            {
                                ExternMetrics.CalculateFunctionDefinition(newFiller, definition, classOrHeaderWithPath);
                            }
                            break;
                        }
                    }
                }
            }
            filler.nameSpaceMetrics.Add(newFiller);
        }
    }
};