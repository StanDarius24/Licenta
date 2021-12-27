package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.CastExpression
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCastExpression

object CastExpressionService {

    fun solveCastExpression(cppastCastExpression: CPPASTCastExpression, statement: Statement?) {
        val castExpr = CastExpression(null, null)
        val anonimStatement = AnonimStatement(null)
        ASTNodeService.solveASTNode(cppastCastExpression.operand as ASTNode, anonimStatement)
        castExpr.operand = anonimStatement.statement
        val anonimStatement1 = AnonimStatement(null)
        ASTNodeService.solveASTNode(cppastCastExpression.typeId as ASTNode,  anonimStatement1)
        castExpr.typeId = anonimStatement1
        StatementMapper.addStatementToStatement(statement!!, castExpr)
    }
}