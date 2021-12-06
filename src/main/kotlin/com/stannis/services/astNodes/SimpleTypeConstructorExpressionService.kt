package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.SimpleTypeConstructorExpression
import com.stannis.services.FunctionCallsService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConstructorInitializer
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleTypeConstructorExpression

class SimpleTypeConstructorExpressionService {
    fun solveTypeConstructorExpre(expression: CPPASTSimpleTypeConstructorExpression, statement: Statement) {
        val simpleTypeConstr = SimpleTypeConstructorExpression(
            expression.declSpecifier.rawSignature, null
        )
        FunctionCallsService.getInstance().declarationStatementForArgumentType(
            (expression.initializer as CPPASTConstructorInitializer).arguments,
            simpleTypeConstr
        )
        StatementMapper.addStatementToStatement(statement, simpleTypeConstr)
    }
}