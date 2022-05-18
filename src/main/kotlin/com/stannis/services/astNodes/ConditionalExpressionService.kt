package com.stannis.services.astNodes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.ConditionalExpression
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConditionalExpression

object ConditionalExpressionService {

    private fun solveparameter(node: ASTNode?, statement: Statement?) {
        if (node != null) {
            ASTNodeService.solveASTNode(node, statement)
        }
    }

    fun solveConditionalExpression(
        conditionalExpression: CPPASTConditionalExpression,
        statement: Statement?
    ) {
        val condExpr = ConditionalExpression(condition = null, negativeResult = null, positiveResult = null)
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        solveparameter(conditionalExpression.logicalConditionExpression as ASTNode, anonimStatement)
        condExpr.condition = anonimStatement.statement as Arguments
        solveparameter(conditionalExpression.positiveResultExpression as ASTNode, anonimStatement)
        condExpr.positiveResult = anonimStatement.statement as Arguments
        solveparameter(conditionalExpression.negativeResultExpression as ASTNode, anonimStatement)
        condExpr.negativeResult = anonimStatement.statement as Arguments
        StatementMapper.addStatementToStatement(statement!!, condExpr)
    }
}
