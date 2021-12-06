package com.stannis.declSpecifier

import com.google.inject.Inject
import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.Return
import com.stannis.services.ConditionalExpressionService
import com.stannis.services.FunctionCallsService
import com.stannis.services.MethodService
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ReturnStatementService {

    private var conditionalExpressionService = ConditionalExpressionService()

    fun solveReturnStatement(data: CPPASTReturnStatement, method: Method?, functionCallsService: FunctionCallsService, methodService: MethodService) {
        val returnT = Return(null, null, null)
        if(data.returnValue != null) {
            if (data.returnValue is CPPASTLiteralExpression || data.returnValue is CPPASTIdExpression || data.returnValue is CPPASTUnaryExpression || data.returnValue is CPPASTFieldReference) {
                returnT.add(data.returnValue.rawSignature)
                println(data.returnValue.rawSignature)
            } else {
                when (data.returnValue) {
                    is CPPASTBinaryExpression -> {
                        functionCallsService.getOperands(data.returnValue as CPPASTBinaryExpression, returnT)
                    }
                    is CPPASTFunctionCallExpression -> {
                        functionCallsService.getOperandsAsFunctionCall(
                            data.returnValue as CPPASTFunctionCallExpression,
                            returnT
                        )
                    }
                    is CPPASTConditionalExpression -> {
                        conditionalExpressionService.solveConditionalExpression(
                            data.returnValue as CPPASTConditionalExpression,
                            method as Statement
                        )
                    }
                    is CPPASTNewExpression -> {
                        println(data.returnValue)
                        //TODO
                    }
                    is CPPASTSimpleTypeConstructorExpression -> {
                        println(data.returnValue)
                        //TODO
                    }
                    is CPPASTCastExpression -> {
                        println(data.returnValue)
                        //TODO
                    }
                    is CPPASTArraySubscriptExpression -> {
                        println(data.returnValue)
                        //TODO
                    }
                    is CPPASTLambdaExpression -> {
                        println(data.returnValue)
                        //TODO
                    }
                    else -> {
                        throw Exception()
                    }
                }
            }
        }
        methodService.addStatement(method!!, returnT)
    }
}