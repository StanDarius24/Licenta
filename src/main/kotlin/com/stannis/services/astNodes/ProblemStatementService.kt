package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ProblemStatement
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTProblemStatement

class ProblemStatementService {

    companion object{
        private lateinit var problemStatementService: ProblemStatementService

        fun getInstance(): ProblemStatementService {
            if(!::problemStatementService.isInitialized) {
                problemStatementService = ProblemStatementService()
            }
            return problemStatementService
        }
    }

    fun solveProblemStatement(problemStm: CPPASTProblemStatement, statement: Statement?) {
        val problemStatement = ProblemStatement(problemStm.rawSignature)
        StatementMapper.addStatementToStatement(statement!!, problemStatement)
    }
}