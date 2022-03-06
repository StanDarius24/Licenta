package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.LambdaExpression
import com.stannis.dataModel.statementTypes.Name
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLambdaExpression

object LambdaExpressionService {
    fun solveLambdaExpression(data: CPPASTLambdaExpression, statement: Statement?) {
        val lambdaExpression = LambdaExpression(null, null, null, null, null, null)
        if (data.captureDefault != null) {
            val name = Name(data.captureDefault.name)
            lambdaExpression.captureDefault = name
        }

        if (data.body != null) {
            val anonimStatement = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.body as ASTNode, anonimStatement)
            lambdaExpression.body = anonimStatement.statement
        }

        if (data.declarator != null) {
            val anonimStatement = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.declarator as ASTNode, anonimStatement)
            lambdaExpression.declarator = anonimStatement.statement
        }

        if (data.closureTypeName != null) {
            val anonimStatement = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.closureTypeName as ASTNode, anonimStatement)
            lambdaExpression.closureTypeName = anonimStatement.statement
        }

        StatementMapper.addStatementToStatement(statement!!, lambdaExpression)
    }
}
