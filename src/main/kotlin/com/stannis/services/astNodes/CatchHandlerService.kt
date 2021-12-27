package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.CatchHandler
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCatchHandler

object CatchHandlerService {
    fun solveCatchHandler(cppastCatch: CPPASTCatchHandler, statement: Statement?) {
        val catchHandler = CatchHandler(null)
        val anonimStatement = AnonimStatement(null)
        ASTNodeService.solveASTNode(cppastCatch.catchBody as ASTNode, anonimStatement)
        catchHandler.body = anonimStatement.statement
        StatementMapper.addStatementToStatement(statement!!, catchHandler)
    }
}