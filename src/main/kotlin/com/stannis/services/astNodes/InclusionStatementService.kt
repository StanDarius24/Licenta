package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.InclusionStatement
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement

object InclusionStatementService {
    fun solveInclusionStatement(
        inclusion: IASTPreprocessorIncludeStatement,
        statement: Statement?
    ) {
        val inclusionStatement = InclusionStatement(inclusion.toString())
        StatementMapper.addStatementToStatement(statement!!, inclusionStatement)
    }
}
