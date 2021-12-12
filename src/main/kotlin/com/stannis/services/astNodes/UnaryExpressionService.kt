package com.stannis.services.astNodes

import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUnaryExpression

class UnaryExpressionService {

    companion object{
        private lateinit var unaryExpressionService: UnaryExpressionService

        fun getInstance(): UnaryExpressionService{
            if(!::unaryExpressionService.isInitialized) {
                unaryExpressionService = UnaryExpressionService()
            }
            return unaryExpressionService
        }
    }

    fun solveUneryExpression(data: CPPASTUnaryExpression, statement: com.stannis.dataModel.Statement?) {
        val initT = com.stannis.dataModel.statementTypes.Statement(data.operand.rawSignature , arrayListOf (data.operator.toString()), null, null, null)
        StatementMapper.addStatementToStatement(statement!!, initT)
    }
}