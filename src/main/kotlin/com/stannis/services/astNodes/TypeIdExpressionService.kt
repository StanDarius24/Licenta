package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.TypeIdExpression
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTypeIdExpression

class TypeIdExpressionService {

    companion object{
        private lateinit var typeIdExpressionService: TypeIdExpressionService

        fun getInstance(): TypeIdExpressionService{
            if(!::typeIdExpressionService.isInitialized) {
                typeIdExpressionService = TypeIdExpressionService()
            }
            return typeIdExpressionService
        }
    }

    fun solveTypeIdExpression(statement: Statement, typeId: CPPASTTypeIdExpression) {
        val typeIdExpr = TypeIdExpression(
            typeId.rawSignature.subSequence(0, typeId.rawSignature.indexOf('(')).toString(),
            typeId.typeId.declSpecifier.rawSignature
        )
        StatementMapper.addStatementToStatement(statement, typeIdExpr)
    }

}