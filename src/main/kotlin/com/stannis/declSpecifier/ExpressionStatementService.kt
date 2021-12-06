package com.stannis.declSpecifier

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.CPPMethodCall
import com.stannis.dataModel.statementTypes.FunctionCall
import com.stannis.dataModel.statementTypes.Initialization
import com.stannis.services.*
import com.stannis.services.astNodes.*
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ExpressionStatementService {

    private fun binaryExpressionSolver(data: CPPASTExpressionStatement, method: Method?) {
        val initialization = Initialization(data.expression.children[0].rawSignature, null, null, null, null)
        FunctionCallsService.getInstance()
            .getOperands(data.expression as CPPASTBinaryExpression, initialization) // new statement structure
        if(initialization.value != null && initialization.value!!.size > 1) {
            initialization.value!!.remove(initialization.value!![initialization.value!!.size - 1])
        }
        StatementMapper.addStatementToStatement(method!!, initialization)
    }

    private fun fieldReferenceSolver(data: CPPASTExpressionStatement, method: Method?) {
        when (((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldOwner) {
            is CPPASTFieldReference -> {
               FieldReferenceService.getInstance()
                   .solveFieldReference(
                       ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldOwner as CPPASTFieldReference,
                       method
                   )
            }
            is CPPASTIdExpression -> {
                val fcall = FunctionCall(
                    null,
                    ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldName.rawSignature,
                    null,
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
                ArraySubscriptExpressionService.getInstance().solveArraySubscript(
                    ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference).fieldOwner as CPPASTArraySubscriptExpression,
                    method!!
                    )
            }
            is CPPASTFunctionCallExpression -> {
//                funcCallSolver(data, method)
            }
            else -> {
                throw Exception()
            }
        }
    }

    private fun funcCallSolver(data: CPPASTExpressionStatement, method: Method?) {
        when ((data.expression as CPPASTFunctionCallExpression).functionNameExpression) {
            is CPPASTUnaryExpression -> {
                val functcall = FunctionCall(
                    null,
                    ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTUnaryExpression).operand.rawSignature,
                    null,null, null
                )
                FunctionCallsService.getInstance().getArgumentsType(
                    data.expression as CPPASTFunctionCallExpression,
                    functcall
                )
                StatementMapper.addStatementToStatement(method!!, functcall)
            }
            is CPPASTIdExpression -> {
                val functcall = FunctionCall(
                    null,
                    ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTIdExpression).name.rawSignature,
                    null, null, null
                )
                FunctionCallsService.getInstance().getArgumentsType(
                    data.expression as CPPASTFunctionCallExpression,
                    functcall
                )
                StatementMapper.addStatementToStatement(method!!, functcall)
            }
            is CPPASTFieldReference -> {
                FieldReferenceService.getInstance()
                    .solveFieldReference(
                        (data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTFieldReference,
                        method
                    )
            }
            else -> {
                throw Exception()
            }
        }
    }

    fun solveExpressionStatement(data: CPPASTExpressionStatement, method: Method?) {
            ASTNodeService.getInstance()
                .solveASTNode(
                    data.expression as ASTNode,
                    method as Statement
                )

    }
}