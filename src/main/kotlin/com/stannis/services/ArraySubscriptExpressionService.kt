package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ArraySubscript
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArraySubscriptExpression

class ArraySubscriptExpressionService {
    fun solveArraySubscript(cppastArraySubscriptExpression: CPPASTArraySubscriptExpression, statement: Statement) {
        val arraySubscr = ArraySubscript(
            cppastArraySubscriptExpression.arrayExpression.rawSignature,
            cppastArraySubscriptExpression.argument.rawSignature
        )
        StatementMapper.addStatementToStatement(statement, arraySubscr)
    }
}