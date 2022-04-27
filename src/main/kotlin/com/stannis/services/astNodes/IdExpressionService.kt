package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.IdExpression
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTIdExpression

object IdExpressionService {
    fun solveIdExpression(node: CPPASTIdExpression, statement: Statement?) {
        val idExpression = IdExpression(expression = null)
        ASTNodeService.solveASTNode(node.name as ASTNode, idExpression)
        StatementMapper.addStatementToStatement(statement!!, idExpression)
    }
}
