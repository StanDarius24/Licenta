package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ParameterDeclaration
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTParameterDeclaration

object ParameterDeclarationService {

    fun solveParameterDeclaration(parameterDeclaration: CPPASTParameterDeclaration, statement: Statement?) {
        StatementMapper.addStatementToStatement(
            statement!!,
            ParameterDeclaration(parameterDeclaration.declSpecifier.rawSignature, parameterDeclaration.declarator.rawSignature))
    }
}