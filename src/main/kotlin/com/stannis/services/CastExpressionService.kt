package com.stannis.services

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.CastExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCastExpression

class CastExpressionService {
    fun solveCastExpression(cppastCastExpression: CPPASTCastExpression, statement: Statement?) {
        val castExpr = CastExpression(
            cppastCastExpression.typeId.rawSignature,
            cppastCastExpression.operand.rawSignature
        )
        StatementMapper.addStatementToStatement(statement!!, castExpr)
    }
}