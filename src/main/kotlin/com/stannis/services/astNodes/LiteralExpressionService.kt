package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.LiteralExpression
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression

object LiteralExpressionService {

    fun solveLiteralExpression(cppastLiteralExpression: CPPASTLiteralExpression, statement: Statement?) {
        val literalExpr = LiteralExpression(cppastLiteralExpression.rawSignature)
        StatementMapper.addStatementToStatement(statement!!, literalExpr)
    }
}