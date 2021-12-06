package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.DeleteExpression
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeleteExpression

class DeleteExpressionService {

    companion object{
        private lateinit var deleteExpressionService: DeleteExpressionService

        fun getInstance(): DeleteExpressionService{
            if(::deleteExpressionService.isInitialized) {
                deleteExpressionService = DeleteExpressionService()
            }
            return deleteExpressionService
        }
    }

    fun solveDeleteExpression(cppastDeleteExpression: CPPASTDeleteExpression, statement: Statement) {
        val delExpression = DeleteExpression(cppastDeleteExpression.operand.rawSignature)
        StatementMapper.addStatementToStatement(statement, delExpression)
    }
}