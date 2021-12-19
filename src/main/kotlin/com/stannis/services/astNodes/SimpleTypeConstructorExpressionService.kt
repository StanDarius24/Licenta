package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.SimpleTypeConstructorExpression
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleTypeConstructorExpression

object SimpleTypeConstructorExpressionService {

    fun solveTypeConstructorExpre(expression: CPPASTSimpleTypeConstructorExpression, statement: Statement) {
        val simpleTypeConstr = SimpleTypeConstructorExpression(
            expression.declSpecifier.rawSignature, null
        )
        ASTNodeService.getInstance()
            .solveASTNode(expression.initializer as ASTNode, simpleTypeConstr)
        StatementMapper.addStatementToStatement(statement, simpleTypeConstr)
    }
}