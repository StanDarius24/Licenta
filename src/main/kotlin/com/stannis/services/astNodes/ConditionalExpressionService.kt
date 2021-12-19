package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.ConditionalExpression
import com.stannis.services.mapper.StatementMapper
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConditionalExpression

object ConditionalExpressionService {

    private fun solveparameter(node: ASTNode?, statement: Statement?) {
        if(node != null) {
            ASTNodeService.getInstance().solveASTNode(
                node,
                statement
            )
        }
    }

    fun solveConditionalExpression(conditionalExpression: CPPASTConditionalExpression, statement: Statement?) {
        val condExpr = ConditionalExpression(
            null, null, null
        )
        val anonimStatement = AnonimStatement(null)
        solveparameter(
            conditionalExpression.logicalConditionExpression as ASTNode,
            anonimStatement
            )
        condExpr.condition = anonimStatement
        solveparameter(
            conditionalExpression.positiveResultExpression as ASTNode,
            anonimStatement
        )
        condExpr.positiveResult = anonimStatement
        solveparameter(
            conditionalExpression.negativeResultExpression as ASTNode,
            anonimStatement
        )
        condExpr.negativeResult = anonimStatement
        StatementMapper.addStatementToStatement(statement!!, condExpr)
    }
}