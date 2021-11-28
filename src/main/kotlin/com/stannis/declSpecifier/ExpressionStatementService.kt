package com.stannis.declSpecifier

import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.CPPMethodCall
import com.stannis.dataModel.statementTypes.FunctionCall
import com.stannis.dataModel.statementTypes.Initialization
import com.stannis.services.FunctionCallsService
import com.stannis.services.MethodService
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ExpressionStatementService {

    private fun binaryExpressionSolver(data: CPPASTExpressionStatement, method: Method?, functionCallsService: FunctionCallsService, methodService: MethodService) {
        val initialization = Initialization(data.expression.children[0].rawSignature, null, null, null)
        functionCallsService.getOperands(data.expression as CPPASTBinaryExpression, initialization) // new statement structure
        if(initialization.value != null && initialization.value!!.size > 1) {
            initialization.value!!.remove(initialization.value!![initialization.value!!.size - 1])
        }
        methodService.addStatement(method!!, initialization)
    }

    private fun fieldReferenceSolver(data: CPPASTExpressionStatement, method: Method?) {
        if(((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldOwner is CPPASTFieldReference) {
            var methodName =
                (((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldOwner as CPPASTFieldReference).fieldOwner.rawSignature
            methodName =
                methodName + "->" + (((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldOwner as CPPASTFieldReference).fieldName.rawSignature
            val cpastmethod = CPPMethodCall(methodName, null)
            val fcall = FunctionCall(
                null,
                ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldName.rawSignature,
                null, null
            )
            cpastmethod.add(fcall)
            (data.expression as CPPASTFunctionCallExpression).arguments.iterator()
                .forEachRemaining { expression ->
                    run {
                        if (expression is CPPASTIdExpression) {
                            fcall.add(expression.name.rawSignature)
                        }
                    }
                }
            method!!.addStatement(cpastmethod)
        } else if(((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldOwner is CPPASTIdExpression) {
            val fcall = FunctionCall(
                null,
                ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldName.rawSignature,
                null,
                null
            )
            (data.expression as CPPASTFunctionCallExpression).arguments.iterator().forEachRemaining {
                parameter ->
                run {
                    fcall.addParameters(parameter.rawSignature) //TODO fix for other method call and make it general line 35
                }
            }
            val cpastmethod = CPPMethodCall(((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldOwner.rawSignature, null)
            cpastmethod.add(fcall)
            method!!.addStatement(cpastmethod)
        } else {
            throw Exception()
        }
    }

    private fun funcCallSolver(data: CPPASTExpressionStatement, method: Method?, functionCallsService: FunctionCallsService, methodService: MethodService) {
        if((data.expression as CPPASTFunctionCallExpression).functionNameExpression is CPPASTUnaryExpression) {
            val functcall = FunctionCall(
                null,
                ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTUnaryExpression).operand.rawSignature,
                null,null
            )
            functionCallsService.getArgumentsType(
                data.expression as CPPASTFunctionCallExpression,
                functcall
            )
            methodService.addStatement(method!!, functcall) //TODO refactor this
        } else if((data.expression as CPPASTFunctionCallExpression).functionNameExpression is CPPASTIdExpression) {
            val functcall = FunctionCall(
                null,
                ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTIdExpression).name.rawSignature,
                null, null
            )
            functionCallsService.getArgumentsType(
                data.expression as CPPASTFunctionCallExpression,
                functcall
            )
            methodService.addStatement(method!!, functcall)
        } else {
            throw Exception()
        }
    }

    private fun functionCallExprSolver(data: CPPASTExpressionStatement, method: Method?, functionCallsService: FunctionCallsService, methodService: MethodService) {
        if((data.expression as CPPASTFunctionCallExpression).functionNameExpression is CPPASTFieldReference) {
            fieldReferenceSolver(data, method)
        } else {
            funcCallSolver(data, method, functionCallsService, methodService)
        }
    }

    fun solveExpressionStatement(data: CPPASTExpressionStatement, method: Method?, functionCallsService: FunctionCallsService, methodService: MethodService) {
        when (data.expression) {
            is CPPASTBinaryExpression -> {
                binaryExpressionSolver(data, method, functionCallsService, methodService)
            }
            is CPPASTFunctionCallExpression -> {
                functionCallExprSolver(data, method, functionCallsService, methodService)
            }
            is CPPASTUnaryExpression -> {
                val initT = Initialization((data.expression as CPPASTUnaryExpression).operand.rawSignature , arrayListOf ((data.expression as CPPASTUnaryExpression).operator.toString()), null, null)
                methodService.addStatement(method!!, initT)
            }
            else -> throw Exception()
        } // Operator 9 is ++
    }
}