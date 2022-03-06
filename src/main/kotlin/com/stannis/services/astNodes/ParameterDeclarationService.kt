package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.ParameterDeclaration
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTParameterDeclaration

object ParameterDeclarationService {

    fun solveParameterDeclaration(
        parameterDeclaration: CPPASTParameterDeclaration,
        statement: Statement?
    ) {
        val parameterDecl = ParameterDeclaration(null, null)
        val anonimStatement1 = AnonimStatement(null)
        val anonimStatement2 = AnonimStatement(null)
        ASTNodeService.solveASTNode(parameterDeclaration.declSpecifier as ASTNode, anonimStatement1)
        ASTNodeService.solveASTNode(parameterDeclaration.declarator as ASTNode, anonimStatement2)
        parameterDecl.declarationSpecifier = anonimStatement1.statement
        parameterDecl.declarator = anonimStatement2.statement
        StatementMapper.addStatementToStatement(statement!!, parameterDecl)
    }
}
