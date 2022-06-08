using Interpreter.Models.metrics;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;

namespace Interpreter.services.metrics{
    public class NameSpaceMetrics
    {
        public static void CalculateNameSpaceMethodsComplexity(ClassOrHeaderWithPath classOrHeaderWithPath,
            MetricsAditionalData filler)
        {
            foreach (var nameSpace in classOrHeaderWithPath.classOrHeader.namespaces)
            {
                foreach (var declaration in nameSpace.declarations)
                {
                    switch ((declaration as SimpleDeclaration)?.declSpecifier)
                    {
                        case CompositeTypeSpecifier:
                            ClassMethodComplexity.CalculateClassMatrics((declaration as SimpleDeclaration).declSpecifier as CompositeTypeSpecifier, filler, classOrHeaderWithPath, nameSpace);
                            break;
                        case SimpleDeclSpecifier:
                        {
                            ClassMethodComplexity.CheckModifier(declaration as SimpleDeclaration, filler);
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
                            break;
                        }
                        default:
                        {
                            if (declaration is FunctionDefinition definition)
                            {
                                filler.numberOfMethods++;
                                filler.totalComplexity += definition.cyclomaticComplexity;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
};