package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.DeclarationStatement
import com.stannis.function.DeclarationRegistry
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarationStatement

object DeclarationStatementService {

    fun solveDeclarationStatement(node: CPPASTDeclarationStatement, statement: Statement?) {
        val declarationStatement = DeclarationStatement(declarations = null)
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(node.declaration as ASTNode, anonimStatement)
        declarationStatement.addDeclaration(anonimStatement.statement as Statement)
        DeclarationRegistry.listOfDeclaration.add(declarationStatement)
        StatementMapper.addStatementToStatement(statement!!, declarationStatement)
    }
}
