package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ArraySubscript
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArraySubscriptExpression

class ArraySubscriptExpressionService {

    companion object {

        private lateinit var arraySubscriptExpressionService: ArraySubscriptExpressionService

        fun getInstance(): ArraySubscriptExpressionService {
            if (!Companion::arraySubscriptExpressionService.isInitialized) {
                arraySubscriptExpressionService = ArraySubscriptExpressionService()
            }
            return arraySubscriptExpressionService
        }
    }

    fun solveArraySubscript(cppastArraySubscriptExpression: CPPASTArraySubscriptExpression, statement: Statement) {
        val arraySubscr = ArraySubscript(
            cppastArraySubscriptExpression.arrayExpression.rawSignature,
            cppastArraySubscriptExpression.argument.rawSignature
        )
        StatementMapper.addStatementToStatement(statement, arraySubscr)
    }
}