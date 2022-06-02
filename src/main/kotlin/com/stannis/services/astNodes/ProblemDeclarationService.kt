package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ProblemDeclaration
import com.stannis.function.ExternalRegistry
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTProblemDeclaration

object ProblemDeclarationService {

    fun solveProblemDeclaration(
        problemDeclaration: CPPASTProblemDeclaration,
        statement: Statement?
    ) {
        val data = ProblemDeclaration(expression = problemDeclaration.rawSignature)
        if(problemDeclaration.rawSignature.contains("EXPORT")) {
            ExternalRegistry.addExternal(problemDeclaration.rawSignature)
        }
        StatementMapper.addStatementToStatement(statement!!, data)
    }
}
