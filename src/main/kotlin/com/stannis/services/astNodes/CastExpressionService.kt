package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.CastExpression
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCastExpression

class CastExpressionService {

    companion object{

        private lateinit var castExpressionService: CastExpressionService

        fun getInstance(): CastExpressionService{
            if(!::castExpressionService.isInitialized) {
                castExpressionService = CastExpressionService()
            }
            return castExpressionService
        }
    }

    fun solveCastExpression(cppastCastExpression: CPPASTCastExpression, statement: Statement?) {
        val castExpr = CastExpression(
            cppastCastExpression.typeId.rawSignature,
            cppastCastExpression.operand.rawSignature
        )
        StatementMapper.addStatementToStatement(statement!!, castExpr)
    }
}