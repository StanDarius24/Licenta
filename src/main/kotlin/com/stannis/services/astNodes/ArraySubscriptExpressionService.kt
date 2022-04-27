package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ArraySubscript
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArraySubscriptExpression

object ArraySubscriptExpressionService {

    fun solveArraySubscript(
        cppastArraySubscriptExpression: CPPASTArraySubscriptExpression,
        statement: Statement
    ) {
        val arraySubscr =
            ArraySubscript(
                arrayValue = cppastArraySubscriptExpression.arrayExpression.rawSignature,
                index = cppastArraySubscriptExpression.argument.rawSignature
            )
        StatementMapper.addStatementToStatement(statement, arraySubscr)
    }
}
