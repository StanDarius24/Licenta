package com.stannis.declSpecifier

import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.CPPMethodCall
import com.stannis.dataModel.statementTypes.FunctionCall
import com.stannis.dataModel.statementTypes.Initialization
import com.stannis.services.*
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ExpressionStatementService {

    private var unaryExpressionService = UnaryExpressionService()
    private var fieldReferenceService = FieldReferenceService()
    private var deleteExpressionService = DeleteExpressionService()
    private var castExpressionService = CastExpressionService()
    private var literalExpressionService = LiteralExpressionService()
    private var arraySubscriptExpressionService = ArraySubscriptExpressionService()

    private fun binaryExpressionSolver(data: CPPASTExpressionStatement, method: Method?, functionCallsService: FunctionCallsService, methodService: MethodService) {
        val initialization = Initialization(data.expression.children[0].rawSignature, null, null, null)
        functionCallsService.getOperands(data.expression as CPPASTBinaryExpression, initialization) // new statement structure
        if(initialization.value != null && initialization.value!!.size > 1) {
            initialization.value!!.remove(initialization.value!![initialization.value!!.size - 1])
        }
        methodService.addStatement(method!!, initialization)
    }

    private fun fieldReferenceSolver(data: CPPASTExpressionStatement, method: Method?, methodService: MethodService) {
        when (((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldOwner) {
            is CPPASTFieldReference -> {
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
            }
            is CPPASTIdExpression -> {
                val fcall = FunctionCall(
                    null,
                    ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldName.rawSignature,
                    null,
                    null
                )
                (data.expression as CPPASTFunctionCallExpression).arguments.iterator().forEachRemaining { parameter ->
                    run {
                        fcall.addParameters(parameter.rawSignature) //TODO fix for other method call and make it general line 35
                    }
                }
                val cpastmethod = CPPMethodCall(((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldOwner.rawSignature, null)
                cpastmethod.add(fcall)
                method!!.addStatement(cpastmethod)
            }
            is CPPASTArraySubscriptExpression -> {
                arraySubscriptExpressionService.solveArraySubscript(
                    ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldOwner as CPPASTArraySubscriptExpression,
                    method,
                    methodService
                    )
                //TODO
            }
            is CPPASTFunctionCallExpression -> {
                //TODO
            }
            else -> {
                throw Exception()
            }
        }
    }

    private fun funcCallSolver(data: CPPASTExpressionStatement, method: Method?, functionCallsService: FunctionCallsService, methodService: MethodService) {
        when ((data.expression as CPPASTFunctionCallExpression).functionNameExpression) {
            is CPPASTUnaryExpression -> {
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
            }
            is CPPASTIdExpression -> {
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
            }
            else -> {
                throw Exception()
            }
        }
    }

    private fun functionCallExprSolver(data: CPPASTExpressionStatement, method: Method?, functionCallsService: FunctionCallsService, methodService: MethodService) {
        if((data.expression as CPPASTFunctionCallExpression).functionNameExpression is CPPASTFieldReference) {
            fieldReferenceSolver(data, method, methodService)
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
                unaryExpressionService.solveUneryExpression(data, method, methodService)
            }
            is CPPASTFieldReference -> {
                fieldReferenceService.solveFieldReference(data.expression as CPPASTFieldReference, method, methodService)
            }
            is CPPASTDeleteExpression -> {
                deleteExpressionService.solveDeleteExpression(data.expression as CPPASTDeleteExpression, method, methodService)
            }
            is CPPASTCastExpression -> {
                castExpressionService.solveCastExpression(data.expression as CPPASTCastExpression, method, methodService)
            }
            is CPPASTLiteralExpression -> {
                literalExpressionService.solveLiteralExpression(data.expression as CPPASTLiteralExpression, method, methodService)
            }
            else -> throw Exception()
        } // Operator 9 is ++
    }
}