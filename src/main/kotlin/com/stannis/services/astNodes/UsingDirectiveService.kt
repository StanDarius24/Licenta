package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.UsingDirective
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUsingDirective

object UsingDirectiveService {

    fun solveUsingDirective(directive: CPPASTUsingDirective, statement: Statement?) {
        val usingDirective = UsingDirective(directive.qualifiedName.rawSignature)
        StatementMapper.addStatementToStatement(statement!!, usingDirective)
    }
}
