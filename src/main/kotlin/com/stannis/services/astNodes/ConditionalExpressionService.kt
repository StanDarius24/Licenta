package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.ConditionalExpression
import com.stannis.services.mapper.StatementMapper
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConditionalExpression

class ConditionalExpressionService {

    companion object {
        private lateinit var conditionalExpressionService: ConditionalExpressionService

        fun getInstance(): ConditionalExpressionService{
            if(!::conditionalExpressionService.isInitialized) {
                conditionalExpressionService = ConditionalExpressionService()
            }
            return conditionalExpressionService
        }
    }

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