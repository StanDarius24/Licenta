package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.DoStatement
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDoStatement

object DoStatementService {

    fun solveDoStatement(doStatement: CPPASTDoStatement, statement: Statement?) {
        val doStm = DoStatement(null, null)
        val anonimStm = AnonimStatement(null)
        ASTNodeService.solveASTNode(doStatement.condition as ASTNode, doStm)
        (doStatement.body as CPPASTCompoundStatement).statements
            .iterator().forEachRemaining { iastStatement ->
                run {
                    ASTNodeService.solveASTNode(iastStatement as ASTNode, anonimStm)
                    doStm.addToBody(anonimStm)
                }
            }
        StatementMapper.addStatementToStatement(statement!!, doStm)
    }
}