package com.stannis.services.cppastService

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*
import com.stannis.declSpecifier.*
import com.stannis.parser.reader.visitor.ASTVisitorOverride
import com.stannis.services.*
import com.stannis.services.astNodes.*
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ASTNodeService {

    private var modifier = "public"

    companion object{
        private lateinit var astNodeService: ASTNodeService

        fun getInstance(): ASTNodeService {
            if(!::astNodeService.isInitialized) {
                astNodeService = ASTNodeService()
            }
            return astNodeService
        }
    }

    fun solveASTNode(node: ASTNode, statement: Statement?) {
        when (node) {
            is CPPASTBinaryExpression -> {
                BinaryExpressionService.getInstance().solveBinaryExpressionService(node, statement)
            }
            is CPPASTFunctionCallExpression -> {
                FunctionCallsService.getInstance().setFunctionCallExpression(node, statement)
            }
            is CPPASTUnaryExpression -> {
                UnaryExpressionService.getInstance().solveUneryExpression(node, statement)
            }
            is CPPASTFieldReference -> {
                FieldReferenceService.getInstance().solveFieldReference(node, statement)
            }
            is CPPASTDeleteExpression -> {
                DeleteExpressionService.getInstance().solveDeleteExpression(node, statement)
            }
            is CPPASTCastExpression -> {
                CastExpressionService.getInstance().solveCastExpression(node, statement)
            }
            is CPPASTLiteralExpression -> {
                LiteralExpressionService.getInstance().solveLiteralExpression(node, statement)
            }
            is CPPASTIdExpression -> {
                StatementMapper.addStatementToStatement(
                    statement!!,
                    IdExpression(node.rawSignature)
                )
            }
            is CPPASTNewExpression -> {
                NewExpressionService.getInstance()
                    .solveNewExpression(node, statement!!)
            }
            is CPPASTConditionalExpression -> {
                ConditionalExpressionService.getInstance()
                    .solveConditionalExpression(node, statement!!)
            }
            is CPPASTArraySubscriptExpression -> {
                ArraySubscriptExpressionService.getInstance()
                    .solveArraySubscript(node, statement!!)
            }
            is CPPASTExpressionList -> {
                ExpressionListService.getInstance()
                    .solveExpressionList(node, statement!!)
            }
            is CPPASTTypeIdExpression -> {
                TypeIdExpressionService.getInstance().solveTypeIdExpression(
                    statement!!,
                    node
                )
            }
            is CPPASTSimpleTypeConstructorExpression -> {
                SimpleTypeConstructorExpressionService.getInstance().solveTypeConstructorExpre(
                    node,
                    statement!!
                )
            }
            is CPPASTIfStatement -> {
                IfStatementService.getInstance()
                    .solveIfStatement(node, statement)
            }
            is CPPASTCompoundStatement -> {
                node.statements.iterator()
                            .forEachRemaining { dataStatement: IASTStatement ->
                                CoreParserClass.seeCPASTCompoundStatement(
                                    dataStatement,
                                    statement
                                )
                            }
            }
            is CPPASTDeclarationStatement -> {

                this.solveASTNode(node.declaration as ASTNode, statement)

//                when (node.declaration) {
//                    is CPPASTSimpleDeclaration -> {
//                        DeclarationStatementParser.getInstance()
//                            .declStatement(node.declaration as CPPASTSimpleDeclaration, statement, modifier)
//                    }
//                    is CPPASTStaticAssertionDeclaration -> {
//                        //TODO
//                    }
//                    is CPPASTAliasDeclaration -> {
//
//                    }
//                    is CPPASTUsingDirective -> {
//
//                    }
//                    else -> {
//                        throw Exception()
//                    }
//                }
            }
            is CPPASTExpressionStatement -> {
                ExpressionStatementService.getInstance()
                    .solveExpressionStatement(node,statement)
            }
            is CPPASTSwitchStatement -> {
                SwitchStatementService.getInstance()
                    .solveSwitchStatement(node, statement)
            }
            is CPPASTProblemStatement -> {
                ProblemStatementService.getInstance()
                    .solveProblemStatement(node, statement)
            }
            is CPPASTReturnStatement -> {
                ReturnStatementService.getInstance()
                    .solveReturnStatement(node, statement)
            }
            is CPPASTDoStatement -> {
                DoStatementService.getInstance()
                    .solveDoStatement(node, statement)
            }
            is CPPASTVisibilityLabel -> {
                modifier = node.rawSignature
            }
            is CPPASTSimpleDeclaration -> {
                val simpleDeclSpecifierService = SimpleDeclSpecifierService()
                if(!simpleDeclSpecifierService.solveDeclSpecifier(
                        node, statement!!, ASTVisitorOverride.getUnit())) {

                }
            }
            is CPPASTFunctionDefinition -> {
                FunctionDefinitionService.getInstance()
                    .handleCPPASTFunctionDefinition(node, statement)
            }
            is CPPASTForStatement -> {
                ForBlockService.getInstance().solveForBlock(node, statement)
            }
            is CPPASTDeclarator -> {
                DeclaratorService.getInstance()
                    .solveDeclaratorService(node, statement)
            }
            is CPPASTBreakStatement -> {
                val breaks = BreakStatement(node.rawSignature)
                StatementMapper.addStatementToStatement(statement!!, breaks)
            }
            is CPPASTWhileStatement -> {
                WhileStatementService.getInstance()
                    .solveWhileStatement(node, statement)
            }
            is CPPASTConstructorInitializer -> {
                ConstructorInitializerService.getInstance()
                    .solveConstructorInitializer(node, statement)
                println(node)
                //TODO
            }
            is CPPASTContinueStatement -> {
                val continueStat =ContinueStatement(node.rawSignature)
                StatementMapper.addStatementToStatement(statement!!, continueStat)
            }
            is CPPASTRangeBasedForStatement -> {
                RangeBaseForStatementService.getInstance()
                    .solveRangeBaseForStatement(node, statement)
            }
            is CPPASTGotoStatement -> {
                val goto = GotoStatement(node.name.rawSignature)
                StatementMapper.addStatementToStatement(statement!!, goto)
            }
            is CPPASTLabelStatement -> {
                LabelStatementService.getInstance()
                    .solveLabelStatement(node, statement)
            }
            is CPPASTNullStatement -> {
                println(node)
                //TODO
            }
            is CPPASTTemplateDeclaration -> {
                println(node)
                //TODO
            }
            is CPPASTProblemDeclaration -> {
                println(node)
                //TODO
            }
            is CPPASTStaticAssertionDeclaration -> {
                println(node)
                //TODO
            }
            is CPPASTAliasDeclaration -> {
                println(node)
                //TODO
            }
            is CPPASTInitializerList -> {
                println(node)
                //TODO
            }
            is CPPASTTryBlockStatement -> {
                println(node)
                //TODO
            }
            is CPPASTUsingDirective -> {
                println(node)
                //TODO
            }
            is CPPASTUsingDeclaration -> {
                println(node)
                //TODO
            }
            is CPPASTEqualsInitializer -> {
                val equals = EqualsInitializer(node.rawSignature, null)
                StatementMapper.addStatementToStatement(statement!!, equals)
            }
            else -> throw Exception()
        }

    }

}