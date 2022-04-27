package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.EnumerationSpecifier
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEnumerationSpecifier

object EnumerationSpecifierService {
    fun solveEnumerationSpecifier(
        cppastEnumerationSpecifier: CPPASTEnumerationSpecifier,
        statement: Statement?
    ) {
        val enumerationSpecifier =
            EnumerationSpecifier(name = cppastEnumerationSpecifier.name.rawSignature, enumerators = null)
        cppastEnumerationSpecifier.enumerators.iterator().forEachRemaining { enumerator ->
            run {
                val anonimStatement = AnonimStatement.getNewAnonimStatement()
                ASTNodeService.solveASTNode(enumerator as ASTNode, anonimStatement)
                enumerationSpecifier.addEnumerators(anonimStatement.statement as Statement)
            }
        }
        StatementMapper.addStatementToStatement(statement!!, enumerationSpecifier)
    }
}
