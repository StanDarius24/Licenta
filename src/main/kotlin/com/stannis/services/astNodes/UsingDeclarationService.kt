package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.UsingDeclaration
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUsingDeclaration
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUsingDirective

object UsingDeclarationService {

    fun solveUnitDeclaration(unitDecl: CPPASTUsingDeclaration, statement: Statement?) {
        val data = UsingDeclaration(unitDecl.name.rawSignature)
        StatementMapper.addStatementToStatement(statement!!, data)
    }
}