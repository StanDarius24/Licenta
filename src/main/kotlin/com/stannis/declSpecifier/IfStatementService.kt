package com.stannis.declSpecifier

import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.If
import com.stannis.services.CoreParserClass
import com.stannis.services.FunctionCallsService
import com.stannis.services.MethodService
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class IfStatementService {
    fun solveIfStatement(data: CPPASTIfStatement,  method: Method?, methodService: MethodService, functionCallsService: FunctionCallsService) {
        println("ifStatement")
        val ifT = If(null, null, null, null, null)
        methodService.addStatement(method!!, ifT)
        if(data.conditionExpression != null) {
            when (data.conditionExpression) {
                is CPPASTBinaryExpression -> {
                    functionCallsService.getOperands(data.conditionExpression as CPPASTBinaryExpression, ifT)
                }
                is CPPASTUnaryExpression -> {
                    ifT.add(data.conditionExpression.rawSignature)
                }
                is CPPASTFunctionCallExpression -> {
                    println(data.conditionExpression) //TODO
                }
                is CPPASTIdExpression -> {
                    println(data.conditionExpression) //TODO
                }
                is CPPASTFieldReference -> {
                    println(data.conditionExpression) //TODO
                }
                is CPPASTLiteralExpression -> {
                    println(data.conditionExpression) //TODO
                }
                is CPPASTArraySubscriptExpression -> {
                    println(data.conditionExpression) //TODO
                }
                else -> {
                    throw Exception()
                }
            }
        }
        val ifBlock = methodService.createMethod()
        ifT.addIfBlock(ifBlock)
        val elseBlock = methodService.createMethod()
        ifT.addElseBlock(elseBlock)
        if(data.thenClause != null)
            CoreParserClass.seeCPASTCompoundStatement(data.thenClause, ifBlock)
        if(data.elseClause != null)
            CoreParserClass.seeCPASTCompoundStatement(data.elseClause, elseBlock)
    }
}