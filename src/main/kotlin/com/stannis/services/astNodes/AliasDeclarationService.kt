package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AliasDeclaration
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTAliasDeclaration

object AliasDeclarationService {
    fun solveAliasDeclarationService(aliasDecl: CPPASTAliasDeclaration, statement: Statement?) {
        StatementMapper.addStatementToStatement(statement!!,
            AliasDeclaration(aliasDecl.alias.rawSignature, aliasDecl.mappingTypeId.rawSignature))
    }
}