package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.CompoundStatement
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement

object CompoundStatementService {

    fun solveCompoundStatement(node: CPPASTCompoundStatement, statement: Statement?) {
        val compoundStatement = CompoundStatement(statements = null)
        node.statements.iterator().forEachRemaining { statements ->
            run {
                val anonimStatement = AnonimStatement.getNewAnonimStatement()
                ASTNodeService.solveASTNode(statements as ASTNode, anonimStatement)
                compoundStatement.addStatement(anonimStatement.statement as Statement)
            }
        }
        StatementMapper.addStatementToStatement(statement!!, compoundStatement)
    }
}
