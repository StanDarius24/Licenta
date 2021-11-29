package com.stannis.declSpecifier

import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.While
import com.stannis.services.CoreParserClass
import com.stannis.services.FunctionCallsService
import com.stannis.services.MethodService
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBinaryExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionCallExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTWhileStatement

class WhileStatementService {

    fun solveWhileStatement(data: CPPASTWhileStatement, method: Method?, methodService: MethodService, functionCallsService: FunctionCallsService) {
        val whileT = While(null, null, null, null)
        methodService.addStatement(method!!, whileT)
        val methodChild = methodService.createMethod()
        whileT.addblock(methodChild)
        when (data.condition) {
            is CPPASTBinaryExpression -> {
                functionCallsService.getOperands(data.condition as CPPASTBinaryExpression, whileT)
            }
            is CPPASTLiteralExpression -> {
                whileT.add(data.condition.rawSignature)
            }
            is CPPASTFunctionCallExpression -> {
                //TODO
            }
            else -> { throw Exception() }
        }
        CoreParserClass.seeCPASTCompoundStatement(data.body, methodChild)
    }
}