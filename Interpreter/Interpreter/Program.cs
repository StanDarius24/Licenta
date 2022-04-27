using System;
using System.Collections.Generic;
using Interpreter.Models;
using Interpreter.Utility;
using Newtonsoft.Json;

namespace Interpreter
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            var text = Reader.ReadFromPath(
                @"C:\Users\Stannis\Desktop\KotlinLicenta\src\main\resources\c++\result\project64-develop\vstudio.sln.json");
            Console.WriteLine(text);
            var data = JsonConvert.DeserializeObject<IList<SolutionModel>>(text, new JsonSerializerSettings
            {
                TypeNameHandling = TypeNameHandling.Auto,
                SerializationBinder = new KnownTypesBinder
                {
                    KnownTypes = new List<Type>
                    {
                        typeof(ComplexCompositeTypeSpecifier),
                        typeof(DeclarationWithParent),
                        typeof(DeclarationWithClass),
                        typeof(FinalTranslation),
                        typeof(FunctionCallWithDeclaration),
                        typeof(TranslationWithPath),
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
                        typeof(RangeBasedForStatement),
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
                        typeof(InitializerList)

                    }
                }
            });
            Console.WriteLine(text);
        }
    }
}