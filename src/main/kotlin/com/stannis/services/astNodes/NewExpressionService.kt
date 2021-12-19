package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionCall
import com.stannis.dataModel.statementTypes.NewExpression
import com.stannis.services.FunctionCallsService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConstructorInitializer
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamedTypeSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNewExpression

object NewExpressionService {

    fun solveNewExpression(node: CPPASTNewExpression, statement: Statement) {
        val newExpression = NewExpression(null)
        val funcCall = FunctionCall(
            null,
            (node.typeId.declSpecifier as CPPASTNamedTypeSpecifier).name.rawSignature,
            null,null,null
        )
        newExpression.addNewExpression(funcCall)
        if(node.initializer != null) {
            FunctionCallsService
                .declarationStatementForArgumentType(
                    (node.initializer as CPPASTConstructorInitializer).arguments,
                    funcCall
                )
        }
        StatementMapper.addStatementToStatement(statement, newExpression)
    }
}