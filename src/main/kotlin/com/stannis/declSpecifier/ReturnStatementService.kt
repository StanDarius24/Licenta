package com.stannis.declSpecifier

import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.Return
import com.stannis.services.FunctionCallsService
import com.stannis.services.MethodService
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ReturnStatementService {

    fun solveReturnStatement(data: CPPASTReturnStatement, method: Method?, functionCallsService: FunctionCallsService, methodService: MethodService) {
        val returnT = Return(null, null)
        if( data.returnValue is CPPASTLiteralExpression || data.returnValue is CPPASTIdExpression || data.returnValue is CPPASTUnaryExpression || data.returnValue is CPPASTFieldReference) {
            returnT.add(data.returnValue.rawSignature)
            println(data.returnValue.rawSignature)
        } else {
            functionCallsService.getOperands(data.returnValue as CPPASTBinaryExpression, returnT)
        }
        methodService.addStatement(method!!, returnT)
    }
}