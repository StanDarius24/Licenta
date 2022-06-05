using System;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;
using Interpreter.services;

namespace Interpreter.Models.metrics{
    public class NameSpaceMetrics
    {
        public static void CalculateNameSpaceMethodsComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsAditionalData filler)
        {
            foreach (var nameSpace in classOrHeaderWithPath.classOrHeader.namespaces)
            {
                foreach (var declaration in nameSpace.declarations)
                {
                    if ((declaration as SimpleDeclaration)?.declSpecifier is CompositeTypeSpecifier)
                    {
                        ClassMethodComplexity.CalculateClassMatrics((declaration as SimpleDeclaration).declSpecifier as CompositeTypeSpecifier, filler, classOrHeaderWithPath, nameSpace);
                    } else if ((declaration as SimpleDeclaration)?.declSpecifier is SimpleDeclSpecifier)
                    {
                        foreach (var functionImplementation in classOrHeaderWithPath.classOrHeader.functionCallsWithoutImplementation)
                        {
                            if (((INameInterface) functionImplementation.name).GetWrittenName()
                                .Equals(
                                    ((INameInterface) ((declaration as SimpleDeclaration).declarators[0] as FunctionDeclarator)!
                                        .name).GetWrittenName()))
                            {
                                filler.totalComplexity += functionImplementation.cyclomaticComplexity;
                                filler.numberOfMethods++;
                            }
                        }
                    } else if (declaration is FunctionDefinition definition)
                    {
                        filler.numberOfMethods++;
                        filler.totalComplexity += definition.cyclomaticComplexity;
                    }
                }
            }
        }
        
        private static void CalculateNameSpaceMethodsComplexitySimpleDeclaration(
            ClassOrHeaderWithPath classOrHeaderWithPath, MetricsAditionalData filler, SimpleDeclaration declaration)
        {
            foreach (var declarationInNamespace in
                     (declaration.declSpecifier as CompositeTypeSpecifier)
                     ?.declarations!)
            {
                if (declarationInNamespace is FunctionDefinition)
                {
                    CalculateNameSpaceMethodsComplexityFunctionDefinition(declarationInNamespace as FunctionDefinition,
                        filler);
                }
                else if (declarationInNamespace is SimpleDeclaration)
                {
                    FixFunctionInDeclaration(classOrHeaderWithPath, declarationInNamespace as SimpleDeclaration);
                }
            }
        }
        
        private static void CalculateNameSpaceMethodsComplexityFunctionDefinition(
            FunctionDefinition declarationInNamespace, MetricsAditionalData filler)
        {
            if (Metrics.IsConstructor(declarationInNamespace))
            {
                filler.numberOfConstructors++;
            }

            filler.numberOfMethods++;
            filler.totalComplexity += (declarationInNamespace).cyclomaticComplexity;
        }
        
        private static void FixFunctionInDeclaration(ClassOrHeaderWithPath classOrHeaderWithPath,
            SimpleDeclaration declarationInNamespace)
        {
            if (declarationInNamespace.declarators[0] is FunctionDeclarator)
            {
                var name =
                    ((declarationInNamespace.declarators[0] as
                        FunctionDeclarator)?.name as INameInterface)?.GetWrittenName();
                foreach (var functionDeclarator in classOrHeaderWithPath.classOrHeader
                             .functionCallsWithoutImplementation)
                {
                    if (((INameInterface) functionDeclarator.name).GetWrittenName().Equals(name))
                    {
                        Console.Out.Write("test");
                    }
                }
            }
        }
    }
};