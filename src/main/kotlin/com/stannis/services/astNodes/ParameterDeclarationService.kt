package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ParameterDeclaration
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTParameterDeclaration

object ParameterDeclarationService {
    fun solveParameterDeclaration(paramDecl: CPPASTParameterDeclaration, statement: Statement?) {
        val parDeclaration = ParameterDeclaration(paramDecl.declSpecifier.rawSignature, paramDecl.declarator.rawSignature)
        StatementMapper.addStatementToStatement(statement!!, parDeclaration)
    }
}