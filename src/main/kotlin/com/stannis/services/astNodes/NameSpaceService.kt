package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.NameSpace
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamespaceDefinition

object NameSpaceService {
    fun solveNameSpace(
        declaration: CPPASTNamespaceDefinition,
        statement: Statement?
    ) {
        val nameSpace =
            NameSpace(
                isInline = declaration.isInline,
                name = declaration.name.rawSignature,
                declarations = null
            )
        if (declaration.declarations != null) {
            declaration.declarations.forEach { element ->
                run {
                    val anonimStatement = AnonimStatement.getNewAnonimStatement()
                    ASTNodeService.solveASTNode(element as ASTNode, anonimStatement)
                    if (anonimStatement.statement != null) {
                        nameSpace.addDeclaration(anonimStatement.statement!!)
                    }
                }
            }
        }
        if (statement != null) {
            StatementMapper.addStatementToStatement(statement, nameSpace)
        }
    }
}
