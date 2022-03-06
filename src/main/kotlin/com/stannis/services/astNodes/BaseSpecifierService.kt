package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.BaseSpecifier
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBaseSpecifier

object BaseSpecifierService {
    fun solveBaseSpecifier(cppastBaseSpecifier: CPPASTBaseSpecifier, statement: Statement?) {
        val baseSpecifier = BaseSpecifier(cppastBaseSpecifier.isVirtual, null)
        val anonimStatement = AnonimStatement(null)
        ASTNodeService.solveASTNode(cppastBaseSpecifier.nameSpecifier as ASTNode, anonimStatement)
        baseSpecifier.name = anonimStatement.statement
        StatementMapper.addStatementToStatement(statement!!, baseSpecifier)
    }
}
