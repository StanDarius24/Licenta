using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;
using Interpreter.services;

namespace Interpreter.Models.metrics{
    public class ClassMethodComplexity
    {

        public static void CalculateClassMatrics(CompositeTypeSpecifier classeElement, MetricsAditionalData filler, ClassOrHeaderWithPath classOrHeaderWithPath, NameSpace nameSpace)
        {
            foreach (var declaration in classeElement.declarations)
            {
                switch (declaration)
                {
                    case FunctionDefinition definition:
                    {
                        CalculateClassMethodAndComplexityFunctionDefinition(definition, filler);
                        break;
                    }
                    case SimpleDeclaration simpleDeclaration
                        when simpleDeclaration.declarators[0] is not FunctionDeclarator:
                        continue;
                    case SimpleDeclaration simpleDeclaration:
                    {
                        CalculateClassMethodAndComplexitySimpleDeclaration(simpleDeclaration, classOrHeaderWithPath,
                            filler, classeElement, nameSpace);
                        break;
                    }
                }
            }
        }
        
        public static void CalculateClassMethodAndComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsAditionalData filler)
        {
            foreach (var classeElement in classOrHeaderWithPath.classOrHeader.classList)
            {
                CalculateClassMatrics(classeElement.our_class, filler, classOrHeaderWithPath, null);
            }
        }
        
        private static void CalculateClassMethodAndComplexityFunctionDefinition(FunctionDefinition definition,
            MetricsAditionalData filler)
        {
            if (Metrics.IsConstructor(definition))
            {
                filler.numberOfConstructors++;
            }

            filler.numberOfMethods++;
            filler.totalComplexity += definition.cyclomaticComplexity;
        }
        
        private static void CalculateClassMethodAndComplexitySimpleDeclaration(SimpleDeclaration simpleDeclaration,
            ClassOrHeaderWithPath classOrHeaderWithPath, MetricsAditionalData filler,
            CompositeTypeSpecifier classeElement, NameSpace nameSpace)
        {
            var abstractMethod =
                simpleDeclaration.declarators[0] as FunctionDeclarator;
            foreach (var functionCall in classOrHeaderWithPath.classOrHeader.functionCallsWithoutImplementation)
            {
                if (abstractMethod == null || functionCall.name is not QualifiedName ||
                    !((QualifiedName) functionCall.name).GetWrittenName()
                        .Equals((abstractMethod.name as INameInterface)?.GetWrittenName())) continue;
                var boolean = false;
                foreach (var qualifiers in (functionCall.name as QualifiedName)!.qualifier)
                {
                    if (nameSpace != null)
                    {
                        if (((INameInterface) qualifiers).GetWrittenName().Equals(nameSpace.name))
                        {
                            boolean = true;
                        }
                    }
                    if ((classeElement.name as INameInterface).GetWrittenName().Equals(((INameInterface) qualifiers).GetWrittenName()))
                    {
                        if (nameSpace == null)
                        {
                            filler.totalComplexity += functionCall.cyclomaticComplexity;
                        }
                        else
                        {
                            if (boolean)
                            {
                                filler.numberOfMethods += 1;
                                filler.totalComplexity += functionCall.cyclomaticComplexity;
                            }
                        }
                    }
                }
            }
        }
    }
}