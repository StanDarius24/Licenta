package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.CaseStatement
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCaseStatement

object CaseStatementService {

    fun solveCaseStatement(caseStatement: CPPASTCaseStatement, statement: Statement?) {
        val data = CaseStatement(expression = caseStatement.expression.rawSignature)
        StatementMapper.addStatementToStatement(statement!!, data)
    }
}
