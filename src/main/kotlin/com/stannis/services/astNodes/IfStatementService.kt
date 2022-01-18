package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.If
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object IfStatementService {

    fun solveIfStatement(data: CPPASTIfStatement,  statement: Statement?) {
        val ifStatement = If(null, null, null, null)
        StatementMapper.addStatementToStatement(statement!!, ifStatement)

        if(data.conditionExpression != null) {
            val anonimStatement1 = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.conditionExpression as ASTNode, anonimStatement1)
            ifStatement.condition = anonimStatement1.statement
        }

        if(data.thenClause != null) {
            val anonimStatement2 = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.thenClause as ASTNode, anonimStatement2)
            ifStatement.thenClause = anonimStatement2.statement
        }

        if(data.elseClause != null) {
            val anonimStatement3 = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.elseClause as ASTNode, anonimStatement3)
            ifStatement.elseClause = anonimStatement3.statement
        }

        if(data.conditionDeclaration != null) {
            val anonimStatement4 = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.conditionDeclaration as ASTNode, anonimStatement4)
            ifStatement.elseClause = anonimStatement4.statement
        }

    }
}