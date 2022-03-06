package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.SimpleDeclSpecifier
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclSpecifier

object SimpleDeclSpecifierService {
    fun solveSimpleDeclSpecifieService(
        simplDecl: CPPASTSimpleDeclSpecifier,
        statement: Statement?
    ) {
        val simpleDeclaration = SimpleDeclSpecifier(simplDecl.rawSignature)
        StatementMapper.addStatementToStatement(statement!!, simpleDeclaration)
    }
}
