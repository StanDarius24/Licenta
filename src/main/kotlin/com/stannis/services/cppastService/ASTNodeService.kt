package com.stannis.services.cppastService

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionCall
import com.stannis.dataModel.statementTypes.IdExpression
import com.stannis.dataModel.statementTypes.NewExpression
import com.stannis.services.*
import com.stannis.services.astNodes.*
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ASTNodeService {

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
                println(node)
            }
            is CPPASTFunctionCallExpression -> {
                println(node)
                FunctionCallsService.getInstance().setFunctionCallExpression(node, statement)
            }
            is CPPASTUnaryExpression -> {
                println(node)
                UnaryExpressionService.getInstance().solveUneryExpression(node, statement)
            }
            is CPPASTFieldReference -> {
                println(node)
                FieldReferenceService.getInstance().solveFieldReference(node, statement)
            }
            is CPPASTDeleteExpression -> {
                println(node)
                DeleteExpressionService.getInstance().solveDeleteExpression(node, statement)
            }
            is CPPASTCastExpression -> {
                println(node)
                CastExpressionService.getInstance().solveCastExpression(node, statement)
            }
            is CPPASTLiteralExpression -> {
                println(node)
                LiteralExpressionService.getInstance().solveLiteralExpression(node, statement)
            }
            is CPPASTIdExpression -> {
                StatementMapper.addStatementToStatement(
                    statement!!,
                    IdExpression(node.rawSignature)
                )
                println(node)
            }
            is CPPASTNewExpression -> {
                NewExpressionService.getInstance()
                    .solveNewExpression(
                        node,
                        statement!!
                    )
            }
            is CPPASTConditionalExpression -> {
                println(node)
                //TODO
            }
            else -> throw Exception()
        }

    }

}