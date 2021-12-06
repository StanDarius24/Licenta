package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.Initialization
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionStatement
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

    fun solveUneryExpression(data: CPPASTExpressionStatement, statement: Statement?) {
        val initT = Initialization((data.expression as CPPASTUnaryExpression).operand.rawSignature , arrayListOf ((data.expression as CPPASTUnaryExpression).operator.toString()), null, null, null)
        StatementMapper.addStatementToStatement(statement!!, initT)
    }

    fun solveUneryExpression(data: CPPASTUnaryExpression, statement: Statement?) {
        val initT = Initialization(data.operand.rawSignature , arrayListOf (data.operator.toString()), null, null, null)
        StatementMapper.addStatementToStatement(statement!!, initT)
    }
}