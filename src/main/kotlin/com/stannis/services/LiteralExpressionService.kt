package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.LiteralExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression

class LiteralExpressionService {
    fun solveLiteralExpression(cppastLiteralExpression: CPPASTLiteralExpression, method: Method?, methodService: MethodService) {
        val literalExpr = LiteralExpression(cppastLiteralExpression.rawSignature)
        methodService.addStatement(method!!, literalExpr)
    }
}