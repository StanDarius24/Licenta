package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.Declarator
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarator

object DeclaratorService {

    fun solveDeclaratorService(
        declarator: CPPASTDeclarator,
        statement: Statement?,
        modifier: String
    ) {
        val decl = Declarator(modifier = modifier, name = null, initialization = null)
        if (declarator.name != null) {
            decl.name = declarator.name.rawSignature
        }
        if (declarator.initializer != null) {
            ASTNodeService.solveASTNode(declarator.initializer as ASTNode, decl)
        }
        StatementMapper.addStatementToStatement(statement!!, decl)
    }
}
