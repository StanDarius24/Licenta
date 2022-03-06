package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ElaboratedTypeSpecifier
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTElaboratedTypeSpecifier

object ElaboratedTypeSpecifierService {
    fun solveElaboratedTypeSpecifier(
        cppastElaboratedTypeSpecifier: CPPASTElaboratedTypeSpecifier,
        statement: Statement?
    ) {
        StatementMapper.addStatementToStatement(
            statement!!,
            ElaboratedTypeSpecifier(cppastElaboratedTypeSpecifier.name.rawSignature)
        )
    }
}
