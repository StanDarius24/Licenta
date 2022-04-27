package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.NamedTypeSpecifier
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamedTypeSpecifier

object NamedTypeSpecifierService {
    fun solveNamedTypeSpecifier(namedTypeSpec: CPPASTNamedTypeSpecifier, statement: Statement?) {
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(namedTypeSpec.name as ASTNode, anonimStatement)
        val namedTypeSpecifier = NamedTypeSpecifier(name = anonimStatement.statement)
        StatementMapper.addStatementToStatement(
            statement!!,
            namedTypeSpecifier
        )
    }
}
