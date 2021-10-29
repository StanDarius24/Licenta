package com.stannis.services

import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.*
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class CoreParserClass {
    companion object {

        private val functionCallsService = FunctionCallsService()
        private val forBlockService = ForBlockService()

        private val methodService = MethodService()


        private fun declStatement(simpleDeclaration: CPPASTSimpleDeclaration, method: Method?) {
            simpleDeclaration.declarators.iterator().forEachRemaining { data ->
                val decl = Declaration(
                    data.name.rawSignature,
                    simpleDeclaration.declSpecifier.rawSignature,
                    data.pointerOperators.size == 1,
                    null,
                    if (data is CPPASTArrayModifier ) {
                        (data as CPPASTArrayDeclarator).arrayModifiers
                            .get(0).constantExpression
                            .rawSignature.toInt()
                    } else { 0 }
                    )
                    functionCallsService.getFunctionCall(data, decl)
                methodService.addDeclaration(method!!, decl)
            }
        }

        fun seeCPASTCompoundStatement(data: IASTStatement, method: Method?) {
            println("---------")
            println(data.rawSignature)
            when (data) {
                is CPPASTDeclarationStatement -> {
                    declStatement(data.declaration as CPPASTSimpleDeclaration, method)
                }
                is CPPASTExpressionStatement -> {
                    println("expr")
                    when (data.expression) {
                        is CPPASTBinaryExpression -> {
                            val initialization = Initialization(data.expression.children[0].rawSignature, null, null, null)
                            functionCallsService.getOperands(data.expression as CPPASTBinaryExpression, initialization) // new statement structure
                            if(initialization.value!!.size > 1) {
                                initialization.value!!.remove(initialization.value!![initialization.value!!.size - 1])
                            }
                            methodService.addStatement(method!!, initialization)
                        }
                        is CPPASTFunctionCallExpression -> {

                            if((data.expression as CPPASTFunctionCallExpression).functionNameExpression is CPPASTFieldReference ) {
                                println((data.expression as CPPASTFunctionCallExpression).functionNameExpression.rawSignature)
                                var methodCall = CPPMethodCall(null, null)
                            // method call with .
                            } else {
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
                        }
                        is CPPASTUnaryExpression -> {
                            val initT = Initialization((data.expression as CPPASTUnaryExpression).operand.rawSignature , arrayListOf ((data.expression as CPPASTUnaryExpression).operator.toString()), null, null)
                            methodService.addStatement(method!!, initT)
                        }
                    } // Operator 9 is ++
                }
                is CPPASTIfStatement -> {
                    println("ifStatement")
                    val ifT = If(null, null, null, null, null)
                    methodService.addStatement(method!!, ifT)
                    functionCallsService.getOperands(data.conditionExpression as CPPASTBinaryExpression, ifT)
                    val ifBlock = methodService.createMethod()
                    ifT.addIfBlock(ifBlock)
                    val elseBlock = methodService.createMethod()
                    ifT.addElseBlock(elseBlock)
                    seeCPASTCompoundStatement(data.thenClause, ifBlock)
                    seeCPASTCompoundStatement(data.elseClause, elseBlock)
                }
                is CPPASTWhileStatement -> {
                    println("while")
                    val whileT = While(null, null, null, null)
                    methodService.addStatement(method!!, whileT)
                    val methodChild = Method(null, null, null, null, null)
                    whileT.addblock(methodChild)
                    functionCallsService.getOperands(data.condition as CPPASTBinaryExpression, whileT)
                    seeCPASTCompoundStatement(data.body, methodChild)
                }
                is CPPASTProblemStatement -> {
                    println("problStatement")
                }
                is CPPASTCompoundStatement -> {
                    println("cmpStat")
                    data.statements.iterator()
                        .forEachRemaining { dataStatement: IASTStatement -> seeCPASTCompoundStatement(dataStatement, method) }
                }
                is CPPASTReturnStatement -> {
                    val returnT = Return(null, null)
                    if( data.returnValue is CPPASTLiteralExpression || data.returnValue is CPPASTIdExpression || data.returnValue is CPPASTUnaryExpression || data.returnValue is CPPASTFieldReference) {
                        returnT.add(data.returnValue.rawSignature)
                        println(data.returnValue.rawSignature)
                    } else {
                        functionCallsService.getOperands(data.returnValue as CPPASTBinaryExpression, returnT)
                    }
                    methodService.addStatement(method!!, returnT)
                }
                is CPPASTForStatement -> {

                forBlockService.solveForBlock(data, method)

                    ((data.initializerStatement as CPPASTDeclarationStatement).declaration as CPPASTSimpleDeclaration).declSpecifier.rawSignature // ( int
                    ((data.initializerStatement as CPPASTDeclarationStatement).declaration as CPPASTSimpleDeclaration).declarators // array of declarations = calculator(4) +dasda etc
                    // declaratirs =>  name = i, initializer initializer Compound Statement fArguments (fOperand1; fOperand2)
                    data.conditionDeclaration //????
                    data.conditionExpression // declarations i < dadsa || dasdw test(x)
                    data.iterationExpression // i = i + dadsawdsa
                    (data.body as CPPASTCompoundStatement).statements // body
                }
            }
            println()
        }

    }
}