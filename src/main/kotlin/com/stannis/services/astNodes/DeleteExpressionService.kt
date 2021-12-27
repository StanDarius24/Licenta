package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.DeleteExpression
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeleteExpression

object DeleteExpressionService {

    fun solveDeleteExpression(cppastDeleteExpression: CPPASTDeleteExpression, statement: Statement?) {
        val delExpression = DeleteExpression(cppastDeleteExpression.operand.rawSignature)
        StatementMapper.addStatementToStatement(statement!!, delExpression)
    }
}