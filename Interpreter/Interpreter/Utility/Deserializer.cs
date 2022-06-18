using System;
using System.Collections.Generic;
using Interpreter.Models.serialize;
using Interpreter.Models.serialize.complexStatementTypes;
using Interpreter.Models.serialize.statementTypes;
using Newtonsoft.Json;

namespace Interpreter.Utility{
    public class Deserializer
    {
        public static IList<RepositoryModel> DeserializeData(string text)
        {
            return JsonConvert.DeserializeObject<IList<RepositoryModel>>(text, new JsonSerializerSettings
            {
                TypeNameHandling = TypeNameHandling.Auto,
                // NullValueHandling = NullValueHandling.Ignore,
                SerializationBinder = new KnownTypesBinder
                {
                    knownTypes = new List<Type>
                    {
                        typeof(FieldReferenceWithParent),
                        typeof(FunctionCallsWithDeclaration),
                        typeof(DeclWithParent),
                        typeof(NameSpace),
                        typeof(CatchHandler),
                        typeof(Capture),
                        typeof(ComplexCompositeTypeSpecifier),
                        typeof(DeclarationWithParent),
                        typeof(DeclarationWithClass),
                        typeof(ClassOrHeader),
                        typeof(ClassOrHeaderWithPath),
                        typeof(AliasDeclaration),      
                        typeof(LabelStatement),
                        typeof(ArraySubscript),         
                        typeof(LambdaExpression),
                        typeof(BaseSpecifier),           
                        typeof(LinkageSpecification),
                        typeof(BinaryExpression),        
                        typeof(LiteralExpression),
                        typeof(BreakStatement),           
                        typeof(Name),
                        typeof(CaseStatement),            
                        typeof(NamedTypeSpecifier),
                        typeof(CastExpression),          
                        typeof(NamespaceAlias),
                        typeof(CompositeTypeSpecifier),   
                        typeof(NewExpression),
                        typeof(CompoundStatement),  
                        typeof(NullStatement),
                        typeof(ConditionalExpression),    
                        typeof(PackEpansionExpression),
                        typeof(ConstructorInitializer),   
                        typeof(ParameterDeclaration),
                        typeof(ContinueStatement),        
                        typeof(ProblemDeclaration),
                        typeof(ConversionName),           
                        typeof(ProblemExpression),
                        typeof(DeclarationStatement),     
                        typeof(ProblemStatement),
                        typeof(Declarator),               
                        typeof(QualifiedName),
                        typeof(DefaultStatement),         
                        typeof(RangeBaseForStatement),
                        typeof(DeleteExpression),         
                        typeof(Return),
                        typeof(DoStatement),              
                        typeof(SimpleDeclSpecifier),
                        typeof(ElaboratedTypeSpecifier),
                        typeof(SimpleDeclaration),
                        typeof(EnumerationSpecifier),  
                        typeof(SimpleTypeConstructorExpression),
                        typeof(Enumerator),               
                        typeof(SimpleTypeTemplateParameter),
                        typeof(EqualsInitializer),        
                        typeof(StaticAssertionDeclaration),
                        typeof(ExpressionList),          
                        typeof(SwitchStatement),
                        typeof(FieldReference),          
                        typeof(TemplateDeclaration),
                        typeof(For),                     
                        typeof(TemplateId),
                        typeof(FunctionCall),            
                        typeof(TemplateSpecialization),
                        typeof(FunctionCalls),          
                        typeof(TryBlockStatement),
                        typeof(FunctionDeclarator),       
                        typeof(TypeId),
                        typeof(FunctionDefinition),       
                        typeof(TypeIdExpression),
                        typeof(GotoStatement),            
                        typeof(UnaryExpression),
                        typeof(IdExpression),            
                        typeof(UsingDeclaration),
                        typeof(If),                   
                        typeof(UsingDirective),
                        typeof(InclusionStatement),      
                        typeof(While),
                        typeof(InitializerList),
                        typeof(RepositoryModel),
                        typeof(SlnStructure),
                        typeof(VcxprojStructure)
                    }
                }
            });
        }
    }
};