package com.stannis.services.astNodes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.NewExpression
import com.stannis.dataModel.statementTypes.TypeId
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNewExpression

object NewExpressionService {

    fun solveNewExpression(node: CPPASTNewExpression, statement: Statement) {
        val newExpression = NewExpression(typeId = null, initializer = null)
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(node.typeId as ASTNode, anonimStatement)
        newExpression.typeId = anonimStatement.statement as TypeId
        if (node.initializer != null) {
            val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
            ASTNodeService.solveASTNode(node.initializer as ASTNode, anonimStatement1)
            newExpression.initializer = anonimStatement1.statement as Arguments
        }
        StatementMapper.addStatementToStatement(statement, newExpression)
    }
}
