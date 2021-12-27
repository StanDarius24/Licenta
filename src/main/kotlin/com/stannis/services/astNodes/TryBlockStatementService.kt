package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.TryBlockStatement
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTryBlockStatement

object TryBlockStatementService {
    fun solveTryBlockStatement(tryBlockStatement: CPPASTTryBlockStatement, statement: Statement?) {
        val tryBlock = TryBlockStatement(null, null)
        tryBlockStatement.catchHandlers.iterator().forEachRemaining { catchHandler -> run {
            val anonimStatement = AnonimStatement(null)
            ASTNodeService.solveASTNode(catchHandler as ASTNode, anonimStatement)
            tryBlock.addCatchHandlers(anonimStatement.statement as Statement)
        } }
        val anonimStatement2 = AnonimStatement(null)
        ASTNodeService.solveASTNode(tryBlockStatement.tryBody as ASTNode, anonimStatement2)
        tryBlock.tryBlock = anonimStatement2.statement
        StatementMapper.addStatementToStatement(statement!!, tryBlock)
    }
}