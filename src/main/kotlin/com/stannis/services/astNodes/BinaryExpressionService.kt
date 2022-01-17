package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.BinaryExpression
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBinaryExpression

object BinaryExpressionService {

    private fun handreOperands(node: ASTNode, statement: Statement) {
       ASTNodeService.solveASTNode(node, statement)
    }

    fun solveBinaryExpressionService(expression: CPPASTBinaryExpression, statement: Statement?) {
        val binaryExpr = BinaryExpression(null, null)
        if(expression.operand1 != null) {
            val anonimStatement = AnonimStatement( null)
            handreOperands(expression.operand1 as ASTNode, anonimStatement)
            binaryExpr.addLeftExpression(anonimStatement.statement!!)
        }
        if(expression.operand2 != null) {
            val anonimStatement = AnonimStatement( null)
            handreOperands(expression.operand2 as ASTNode, anonimStatement)
            binaryExpr.addRightExpression(anonimStatement.statement!!)
        }
        StatementMapper.addStatementToStatement(statement!!, binaryExpr)
    }

}