package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.Initialization
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionStatement
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUnaryExpression

class UnaryExpressionService {

    fun solveUneryExpression(data: CPPASTExpressionStatement, statement: Statement?) {
        val initT = Initialization((data.expression as CPPASTUnaryExpression).operand.rawSignature , arrayListOf ((data.expression as CPPASTUnaryExpression).operator.toString()), null, null, null)
        StatementMapper.addStatementToStatement(statement!!, initT)
    }
}