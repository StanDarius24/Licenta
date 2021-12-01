package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.CastExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCastExpression

class CastExpressionService {
    fun solveCastExpression(cppastCastExpression: CPPASTCastExpression, method: Method?, methodService: MethodService) {
        val castExpr = CastExpression(
            cppastCastExpression.typeId.rawSignature,
            cppastCastExpression.operand.rawSignature
        )
        methodService.addStatement(method!!, castExpr)
    }
}