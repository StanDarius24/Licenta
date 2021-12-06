package com.stannis.services

import com.google.inject.Inject
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.BinaryExpression
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBinaryExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionCallExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression

class BinaryExpressionService {

    private var literalExpressionService = LiteralExpressionService()

    @Inject
    private lateinit var functionCallsService: FunctionCallsService

    private fun handreOperands(node: ASTNode, statement: Statement?) {
        when(node) {
            is CPPASTLiteralExpression -> {
                literalExpressionService.solveLiteralExpression(node, statement!!)
            }
            is CPPASTFunctionCallExpression -> {
                functionCallsService.solve(node, statement!!)
            }
            else -> { throw Exception()}
        }
    }

    fun solveBinaryExpressionService(expression: CPPASTBinaryExpression): Statement {
        val binaryExpr = BinaryExpression(null, null)
        handreOperands(expression.operand1 as ASTNode, binaryExpr.leftExpression)
        handreOperands(expression.operand2 as ASTNode, binaryExpr.rightExpression)
        return binaryExpr
    }

}