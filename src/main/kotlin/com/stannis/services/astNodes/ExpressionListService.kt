package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ExpressionList
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionList

object ExpressionListService {

    fun solveExpressionList(expression: CPPASTExpressionList, statement: Statement) {
        val expressionList = ExpressionList(expressions = null)
        expression.expressions.iterator().forEachRemaining { expressionX ->
            run { ASTNodeService.solveASTNode(expressionX as ASTNode, expressionList) }
        }
        StatementMapper.addStatementToStatement(statement, expressionList)
    }
}
