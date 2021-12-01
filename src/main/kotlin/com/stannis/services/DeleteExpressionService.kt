package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.DeleteExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeleteExpression

class DeleteExpressionService {
    fun solveDeleteExpression(cppastDeleteExpression: CPPASTDeleteExpression, method: Method?, methodService: MethodService) {
        val delExpression = DeleteExpression(cppastDeleteExpression.operand.rawSignature)
        methodService.addStatement(method!!, delExpression)
    }
}