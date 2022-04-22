package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ProblemExpression
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTProblemExpression

object ProblemExpressionService {
    fun solveProblemExpression(node: CPPASTProblemExpression, statement: Statement?) {
        val problemExpression = ProblemExpression(node.rawSignature)
        StatementMapper.addStatementToStatement(statement!!, problemExpression)
    }

}