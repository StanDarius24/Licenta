package com.stannis.services

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ConditionalExpression
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConditionalExpression

class ConditionalExpressionService {
    val nodeService = ASTNodeService()

    fun solveConditionalExpression(conditionalExpression: CPPASTConditionalExpression, statement: Statement) {
        val condExpr = ConditionalExpression(
            nodeService.solveASTNode(conditionalExpression.logicalConditionExpression as ASTNode),
            nodeService.solveASTNode(conditionalExpression.positiveResultExpression as ASTNode),
            nodeService.solveASTNode(conditionalExpression.negativeResultExpression as ASTNode)
        )
        StatementMapper.addStatementToStatement(statement, condExpr)
    }
}