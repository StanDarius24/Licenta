package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.DoStatement
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDoStatement

class DoStatementService {

    companion object{
        private lateinit var doStatementService: DoStatementService

        fun getInstance(): DoStatementService{
            if(!::doStatementService.isInitialized) {
                doStatementService = DoStatementService()
            }
            return doStatementService
        }
    }

    fun solveDoStatement(doStatement: CPPASTDoStatement, statement: Statement?) {
        val doStm = DoStatement(null, null)
        val anonimStm = AnonimStatement(null)
        ASTNodeService.getInstance()
            .solveASTNode(doStatement.condition as ASTNode, doStm)
        (doStatement.body as CPPASTCompoundStatement).statements
            .iterator().forEachRemaining { iastStatement ->
                run {
                    ASTNodeService.getInstance()
                        .solveASTNode(iastStatement as ASTNode, anonimStm)
                    doStm.addToBody(anonimStm)
                }
            }
        StatementMapper.addStatementToStatement(statement!!, doStm)
    }
}