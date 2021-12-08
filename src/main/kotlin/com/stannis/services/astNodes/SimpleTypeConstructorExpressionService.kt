package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.SimpleTypeConstructorExpression
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleTypeConstructorExpression

class SimpleTypeConstructorExpressionService {

    companion object{
        private lateinit var simpleTypeConstructorExpression: SimpleTypeConstructorExpressionService

        fun getInstance(): SimpleTypeConstructorExpressionService{
            if(!::simpleTypeConstructorExpression.isInitialized) {
                simpleTypeConstructorExpression = SimpleTypeConstructorExpressionService()
            }
            return simpleTypeConstructorExpression
        }
    }

    fun solveTypeConstructorExpre(expression: CPPASTSimpleTypeConstructorExpression, statement: Statement) {
        val simpleTypeConstr = SimpleTypeConstructorExpression(
            expression.declSpecifier.rawSignature, null
        )
        ASTNodeService.getInstance()
            .solveASTNode(expression.initializer as ASTNode, simpleTypeConstr)
        StatementMapper.addStatementToStatement(statement, simpleTypeConstr)
    }
}