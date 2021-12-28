package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier

object CompositeTypeSpecifierService {
    fun solveCompositeTypeSpecifier(cppastCompositeTypeSpecifier: CPPASTCompositeTypeSpecifier, statement: Statement?) {
        StatementMapper.addStatementToStatement(statement!!, CompositeTypeSpecifier(cppastCompositeTypeSpecifier.rawSignature))
    }
}