package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.NamedTypeSpecifier
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamedTypeSpecifier

object NamedTypeSpecifierService {
    fun solveNamedTypeSpecifier(namedTypeSpec: CPPASTNamedTypeSpecifier, statement: Statement?) {
        StatementMapper.addStatementToStatement(statement!!, NamedTypeSpecifier(namedTypeSpec.rawSignature))
    }
}