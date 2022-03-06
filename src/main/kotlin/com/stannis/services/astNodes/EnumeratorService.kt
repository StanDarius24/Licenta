package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.Enumerator
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEnumerator

object EnumeratorService {

    fun solveEnumerator(cppastEnumerator: CPPASTEnumerator, statement: Statement?) {
        val enumerator = Enumerator(null, null)
        val anonimStatement1 = AnonimStatement(null)
        ASTNodeService.solveASTNode(cppastEnumerator.name as ASTNode, anonimStatement1)
        enumerator.name = anonimStatement1.statement
        val anonimStatement2 = AnonimStatement(null)
        if (cppastEnumerator.value != null) {
            ASTNodeService.solveASTNode(cppastEnumerator.value as ASTNode, anonimStatement2)
        }
        enumerator.value = anonimStatement2.statement
        StatementMapper.addStatementToStatement(statement!!, enumerator)
    }
}
