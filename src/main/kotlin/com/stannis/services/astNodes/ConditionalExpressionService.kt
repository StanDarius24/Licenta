package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
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

    fun solveConditionalExpression(conditionalExpression: CPPASTConditionalExpression, statement: Statement) {
        val condExpr = ConditionalExpression(
            ASTNodeService.getInstance().solveASTNode(conditionalExpression.logicalConditionExpression as ASTNode),
            ASTNodeService.getInstance().solveASTNode(conditionalExpression.positiveResultExpression as ASTNode),
            ASTNodeService.getInstance().solveASTNode(conditionalExpression.negativeResultExpression as ASTNode)
        )
        StatementMapper.addStatementToStatement(statement, condExpr)
    }
}