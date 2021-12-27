package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.PackEpansionExpression
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTPackExpansionExpression

object PackExpansionExpressionService {
    fun solvePackExpansionExpression(packExpansion: CPPASTPackExpansionExpression, statement: Statement?) {
        val packExpansionExpression = PackEpansionExpression(null)
        val anonimStatement = AnonimStatement(null)
        ASTNodeService.solveASTNode(packExpansion.pattern as ASTNode, anonimStatement)
        packExpansionExpression.pattern = anonimStatement.statement
        StatementMapper.addStatementToStatement(statement!!, packExpansionExpression)
    }
}