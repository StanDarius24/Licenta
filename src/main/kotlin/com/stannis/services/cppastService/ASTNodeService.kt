package com.stannis.services.cppastService

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*
import com.stannis.declSpecifier.*
import com.stannis.services.astNodes.*
import com.stannis.services.astNodes.FunctionDefinitionService
import com.stannis.services.astNodes.SimpleDeclSpecifierService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object ASTNodeService {

    private var modifier = "public"

    fun solveASTNode(node: ASTNode, statement: Statement?) {
        when (node) {
            is CPPASTBinaryExpression -> {
                BinaryExpressionService.solveBinaryExpressionService(node, statement)
            }
            is CPPASTFunctionCallExpression -> {
                FunctionCallsService.solveFunctionCalls(node, statement)
            }
            is CPPASTUnaryExpression -> {
                UnaryExpressionService.solveUneryExpression(node, statement)
            }
            is CPPASTFieldReference -> {
                FieldReferenceService.solveFieldReference(node, statement)
            }
            is CPPASTDeleteExpression -> {
                DeleteExpressionService.solveDeleteExpression(node, statement)
            }
            is CPPASTCastExpression -> {
                CastExpressionService.solveCastExpression(node, statement)
            }
            is CPPASTLiteralExpression -> {
                LiteralExpressionService.solveLiteralExpression(node, statement)
            }
            is CPPASTIdExpression -> {
                StatementMapper.addStatementToStatement(
                    statement!!,
                    IdExpression(node.rawSignature)
                )
            }
            is CPPASTNewExpression -> {
                NewExpressionService
                    .solveNewExpression(node, statement!!)
            }
            is CPPASTConditionalExpression -> {
                ConditionalExpressionService
                    .solveConditionalExpression(node, statement!!)
            }
            is CPPASTArraySubscriptExpression -> {
                ArraySubscriptExpressionService
                    .solveArraySubscript(node, statement!!)
            }
            is CPPASTExpressionList -> {
                ExpressionListService
                    .solveExpressionList(node, statement!!)
            }
            is CPPASTTypeIdExpression -> {
                TypeIdExpressionService.solveTypeIdExpression(
                    node,
                    statement
                )
            }
            is CPPASTSimpleTypeConstructorExpression -> {
                SimpleTypeConstructorExpressionService.solveTypeConstructorExpre(
                    node,
                    statement!!
                )
            }
            is CPPASTIfStatement -> {
                IfStatementService
                    .solveIfStatement(node, statement)
            }
            is CPPASTCompoundStatement -> {
                CompoundStatementService.solveCompoundStatement(node, statement)
            }
            is CPPASTDeclarationStatement -> {
                val declarationStatement = DeclarationStatement(null)
                val anonimStatement = AnonimStatement(null)
                this.solveASTNode(node.declaration as ASTNode, anonimStatement)
                declarationStatement.addDeclaration(anonimStatement.statement as Statement)
                StatementMapper.addStatementToStatement(statement!!, declarationStatement) //refactor this
            }
            is CPPASTExpressionStatement -> {
                ExpressionStatementService
                    .solveExpressionStatement(node,statement)
            }
            is CPPASTSwitchStatement -> {
                SwitchStatementService
                    .solveSwitchStatement(node, statement)
            }
            is CPPASTProblemStatement -> {
                ProblemStatementService
                    .solveProblemStatement(node, statement)
            }
            is CPPASTReturnStatement -> {
                ReturnStatementService
                    .solveReturnStatement(node, statement)
            }
            is CPPASTDoStatement -> {
                DoStatementService
                    .solveDoStatement(node, statement)
            }
            is CPPASTVisibilityLabel -> {
                modifier = node.rawSignature
            }
            is CPPASTSimpleDeclaration -> {
                if(!SimpleDeclarationService.solveDeclSpecifier(
                        node, statement)) {
                    println("data")
                }
            }
            is CPPASTFunctionDefinition -> {
                FunctionDefinitionService.solveFunctionDefinition(node, statement)
            }
            is CPPASTForStatement -> {
                ForBlockService.solveForBlock(node, statement)
            }
            is CPPASTDeclarator -> {
                DeclaratorService
                    .solveDeclaratorService(node, statement)
            }
            is CPPASTBreakStatement -> {
                val breaks = BreakStatement(node.rawSignature)
                StatementMapper.addStatementToStatement(statement!!, breaks)
            }
            is CPPASTWhileStatement -> {
                WhileStatementService
                    .solveWhileStatement(node, statement)
            }
            is CPPASTConstructorInitializer -> {
                ConstructorInitializerService
                    .solveConstructorInitializer(node, statement)
            }
            is CPPASTContinueStatement -> {
                val continueStat =ContinueStatement(node.rawSignature)
                StatementMapper.addStatementToStatement(statement!!, continueStat)
            }
            is CPPASTRangeBasedForStatement -> {
                RangeBaseForStatementService
                    .solveRangeBaseForStatement(node, statement)
            }
            is CPPASTGotoStatement -> {
                val goto = GotoStatement(node.name.rawSignature)
                StatementMapper.addStatementToStatement(statement!!, goto)
            }
            is CPPASTLabelStatement -> {
                LabelStatementService
                    .solveLabelStatement(node, statement)
            }
            is CPPASTNullStatement -> {
                StatementMapper.addStatementToStatement(statement!!, NullStatement(node.rawSignature))
            }
            is CPPASTTemplateDeclaration -> {
                TemplateDeclarationService.solveTemplateDeclaration(node, statement)
            }
            is CPPASTProblemDeclaration -> {
                ProblemDeclarationService.solveProblemDeclaration(node, statement)
            }
            is CPPASTStaticAssertionDeclaration -> {
                StaticAssertionDeclarationService.solveStaticAssertionDeclaration(node, statement!!)
            }
            is CPPASTAliasDeclaration -> {
                AliasDeclarationService.solveAliasDeclarationService(node, statement)
            }
            is CPPASTInitializerList -> {
                InitializerListService.solveInitializerList(node, statement)
            }
            is CPPASTTryBlockStatement -> {
                TryBlockStatementService.solveTryBlockStatement(node, statement)
            }
            is CPPASTUsingDirective -> {
                UsingDirectiveService.solveUsingDirective(node, statement)
            }
            is CPPASTUsingDeclaration -> {
                UsingDeclarationService.solveUnitDeclaration(node, statement)
            }
            is CPPASTEqualsInitializer -> {
                val equals = EqualsInitializer(node.rawSignature, null)
                StatementMapper.addStatementToStatement(statement!!, equals)
            }
            is CPPASTSimpleTypeTemplateParameter -> {
                val simpleType = SimpleTypeTemplateParameter(node.name.rawSignature)
                StatementMapper.addStatementToStatement(statement!!, simpleType)
            }
            is CPPASTParameterDeclaration -> {
                ParameterDeclarationService.solveParameterDeclaration(node, statement)
            }
            is CPPASTCaseStatement -> {
                CaseStatementService.solveCaseStatement(node, statement)
            }
            is CPPASTDefaultStatement -> {
                DefaultStatementService.solveDefaultStatement(node, statement)
            }
            is CPPASTTemplateSpecialization -> {
                TemplateSpecializationService.solveTemplateSpecialization(node, statement)
            }
            is CPPASTSimpleDeclSpecifier -> {
                SimpleDeclSpecifierService.solveSimpleDeclSpecifieService(node, statement)
            }
            is CPPASTNamedTypeSpecifier -> {
                NamedTypeSpecifierService.solveNamedTypeSpecifier(node, statement)
            }
            is CPPASTPackExpansionExpression -> {
                PackExpansionExpressionService.solvePackExpansionExpression(node, statement)
            }
            is CPPASTTypeId -> {
                TypeIdService.solveTypeId(node, statement)
            }
            is CPPASTCatchHandler -> {
                CatchHandlerService.solveCatchHandler(node, statement)
            }
            is CPPASTNamespaceAlias -> {
                NamespaceAliasService.solveNamespaceAlias(node, statement)
            }
            is CPPASTName -> {
                NameService.solveName(node, statement)
            }
            is CPPASTQualifiedName -> {
                QualifiedNameService.solveQualifiedNameService(node, statement)
            }
            is CPPASTLinkageSpecification -> {
                LinkageSpecificationService.solveLinkageSpecification(node, statement)
            }
            is CPPASTCompositeTypeSpecifier -> {
                CompositeTypeSpecifierService.solveCompositeTypeSpecifier(node, statement)
            }
            is CPPASTEnumerationSpecifier -> {
                EnumerationSpecifierService.solveEnumerationSpecifier(node, statement)
            }
            is CPPASTEnumerator -> {
                EnumeratorService.solveEnumerator(node, statement)
            }
            is CPPASTElaboratedTypeSpecifier -> {
                ElaboratedTypeSpecifierService.solveElaboratedTypeSpecifier(node, statement)
            }
            is CPPASTLambdaExpression -> {
                LambdaExpressionService.solveLambdaExpression(node, statement)
            }
            is CPPASTProblemExpression -> {

            }
            else -> throw Exception()
        }

    }

}