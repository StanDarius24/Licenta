package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ExpressionList
import com.stannis.services.FunctionCallsService
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBinaryExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionList
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUnaryExpression

class ExpressionListService {

    companion object{
        private lateinit var expressionListService: ExpressionListService

        fun getInstance(): ExpressionListService{
            if(!::expressionListService.isInitialized) {
                expressionListService = ExpressionListService()
            }
            return expressionListService
        }
    }

    fun solveExpressionList(expression: CPPASTExpressionList, statement: Statement) {
        val expressionList = ExpressionList(null)
        expression.expressions.iterator()
            .forEachRemaining { expression ->
                run {
                    ASTNodeService.getInstance()
                        .solveASTNode(expression as ASTNode, expressionList)
                }
            }
        StatementMapper.addStatementToStatement(statement, expressionList)
    }
}