package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.BinaryExpression
import com.stannis.services.FunctionCallsService
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBinaryExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionCallExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression

class BinaryExpressionService {

    companion object{
        private lateinit var binaryExpression: BinaryExpressionService

        fun getInstance(): BinaryExpressionService {
            if(!Companion::binaryExpression.isInitialized) {
                binaryExpression = BinaryExpressionService()
            }
            return binaryExpression
        }
    }

    private var literalExpressionService = LiteralExpressionService()

    private fun handreOperands(node: ASTNode, statement: Statement) {
        when(node) {
            is CPPASTLiteralExpression -> {
                literalExpressionService.solveLiteralExpression(node, statement!!)
            }
            is CPPASTFunctionCallExpression -> {
                FunctionCallsService.getInstance().solve(node, statement!!)
            }
            else -> { throw Exception()}
        }
    }

    fun solveBinaryExpressionService(expression: CPPASTBinaryExpression): Statement {
        val binaryExpr = BinaryExpression(null, null)
        if(binaryExpr.leftExpression != null) {
            handreOperands(expression.operand1 as ASTNode, binaryExpr.leftExpression!!)
        }
        if(binaryExpr.rightExpression != null) {
            handreOperands(expression.operand2 as ASTNode, binaryExpr.rightExpression!!)
        }
        return binaryExpr
    }

}