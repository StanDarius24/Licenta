package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.LiteralExpression
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression

class LiteralExpressionService {

    companion object{
        private lateinit var literalExpressionService: LiteralExpressionService

        fun getInstance(): LiteralExpressionService {
            if(!::literalExpressionService.isInitialized) {
                literalExpressionService = LiteralExpressionService()
            }
            return literalExpressionService
        }
    }

    fun solveLiteralExpression(cppastLiteralExpression: CPPASTLiteralExpression, statement: Statement?) {
        val literalExpr = LiteralExpression(cppastLiteralExpression.rawSignature)
        StatementMapper.addStatementToStatement(statement!!, literalExpr)
    }
}