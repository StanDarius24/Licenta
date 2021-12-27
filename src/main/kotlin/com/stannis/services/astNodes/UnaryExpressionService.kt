package com.stannis.services.astNodes

import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUnaryExpression

object UnaryExpressionService {

    fun solveUneryExpression(data: CPPASTUnaryExpression, statement: com.stannis.dataModel.Statement?) {
        val initT = com.stannis.dataModel.statementTypes.Statement(data.operand.rawSignature , arrayListOf (data.operator.toString()), null, null, null)
        StatementMapper.addStatementToStatement(statement!!, initT)
    }
}