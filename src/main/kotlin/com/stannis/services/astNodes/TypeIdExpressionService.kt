package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.TypeIdExpression
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTypeIdExpression

object TypeIdExpressionService {

    fun solveTypeIdExpression(statement: Statement, typeId: CPPASTTypeIdExpression) {
        val typeIdExpr = TypeIdExpression(
            typeId.rawSignature.subSequence(0, typeId.rawSignature.indexOf('(')).toString(),
            typeId.typeId.declSpecifier.rawSignature
        )
        StatementMapper.addStatementToStatement(statement, typeIdExpr)
    }

}