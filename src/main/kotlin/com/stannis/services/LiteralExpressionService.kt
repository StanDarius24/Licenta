package com.stannis.services

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.LiteralExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression

class LiteralExpressionService {
    fun solveLiteralExpression(cppastLiteralExpression: CPPASTLiteralExpression, statement: Statement) {
        val literalExpr = LiteralExpression(cppastLiteralExpression.rawSignature)
        StatementMapper.addStatementToStatement(statement, literalExpr)
    }
}