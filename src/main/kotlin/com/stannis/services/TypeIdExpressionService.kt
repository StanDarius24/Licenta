package com.stannis.services

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.TypeIdExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTypeIdExpression

class TypeIdExpressionService {

    fun solveTypeIdExpression(statement: Statement, typeId: CPPASTTypeIdExpression) {
        val typeIdExpr = TypeIdExpression(
            typeId.rawSignature.subSequence(0, typeId.rawSignature.indexOf('(')).toString(),
            typeId.typeId.declSpecifier.rawSignature
        )
        StatementMapper.addStatementToStatement(statement, typeIdExpr)
    }

}