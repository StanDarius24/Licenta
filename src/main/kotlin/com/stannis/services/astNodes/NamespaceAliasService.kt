package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.NamespaceAlias
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamespaceAlias

object NamespaceAliasService {
    fun solveNamespaceAlias(cppastNamespaceAlias: CPPASTNamespaceAlias, statement: Statement?) {
        val namespaceAlias = NamespaceAlias(null, null)
        val anonimStatement = AnonimStatement(null)
        ASTNodeService.solveASTNode(cppastNamespaceAlias.alias as ASTNode, anonimStatement)
        namespaceAlias.alias = anonimStatement.statement
        ASTNodeService.solveASTNode(cppastNamespaceAlias.mappingName as ASTNode, anonimStatement)
        namespaceAlias.qualifiedName = anonimStatement.statement
        StatementMapper.addStatementToStatement(statement!!, namespaceAlias)
    }
}