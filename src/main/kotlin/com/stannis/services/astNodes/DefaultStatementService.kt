package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.DefaultStatement
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDefaultStatement

object DefaultStatementService {

    fun solveDefaultStatement(defaultStatement: CPPASTDefaultStatement, statement: Statement?) {
        throw Exception()
        StatementMapper.addStatementToStatement(statement!!, DefaultStatement(null))
    }
}
