package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.Declarator
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarator

class DeclaratorService {
    companion object {
        private lateinit var declaratorService: DeclaratorService

        fun getInstance(): DeclaratorService {
            if(!::declaratorService.isInitialized) {
                declaratorService = DeclaratorService()
            }
            return declaratorService
        }
    }

    fun solveDeclaratorService(declarator: CPPASTDeclarator, statement: Statement?) {
        val decl = Declarator(declarator.name.rawSignature, null)
        ASTNodeService.getInstance()
            .solveASTNode(declarator.initializer as ASTNode, decl)
        StatementMapper.addStatementToStatement(statement!!, decl)
    }

}