package com.stannis.services

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.SimpleTypeConstructorExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConstructorInitializer
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleTypeConstructorExpression

class SimpleTypeConstructorExpressionService {
    fun solveTypeConstructorExpre(expression: CPPASTSimpleTypeConstructorExpression, statement: Statement, functionCallsService: FunctionCallsService) {
        val simpleTypeConstr = SimpleTypeConstructorExpression(
            expression.declSpecifier.rawSignature, null
        )
        functionCallsService.declarationStatementForArgumentType(
            (expression.initializer as CPPASTConstructorInitializer).arguments,
            simpleTypeConstr
        )
        StatementMapper.addStatementToStatement(statement, simpleTypeConstr)
    }
}