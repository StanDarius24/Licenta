package com.stannis.services

import com.google.inject.Inject
import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.For
import com.stannis.dataModel.statementTypes.Initialization
import com.stannis.parser.reader.visitor.ASTVisitorOverride
import org.eclipse.cdt.core.dom.ast.IASTDeclarator
import org.eclipse.cdt.core.dom.ast.IASTExpression
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ForBlockService {

    private val functionCallsService = FunctionCallsService()
    private val methodService = MethodService()

    fun solveForBlock(data: CPPASTForStatement, method: Method?) {
        val forT = For(null, null, null, null, null)
        methodService.addStatement(method!!, forT)
        solveForInitialization(data.initializerStatement, forT)
        solveForConditionExpression(data.conditionExpression, forT)
        solveForIterationExpression(data.iterationExpression, forT)
        if(data.body != null) {
            val meth = methodService.createMethod()
            forT.addMethod(meth)
                CoreParserClass.seeCPASTCompoundStatement(data.body, meth)
        }
    }

    private fun solveForIterationExpression(iterationExpression: IASTExpression?, forT: For) {
        if( iterationExpression != null) {
            println(iterationExpression.rawSignature)
            val initT = Initialization(null, null, null, null, null)
            when (iterationExpression) {
                is CPPASTExpressionList -> {
                    iterationExpression.expressions.iterator()
                        .forEachRemaining { expression ->
                            run {
                                when (expression) {
                                    is CPPASTBinaryExpression -> { // TODO HANDLE ASTNODESERVICE!
                                        functionCallsService.getOperands(expression, initT)
                                    }
                                    is CPPASTUnaryExpression -> {
                                        initT.add(expression.rawSignature)
                                    }
                                    else -> {
                                        throw Exception()
                                    }
                                }
                            }
                        }
                    forT.addIteration(initT)
                }
                is CPPASTUnaryExpression -> {
                    forT.addIteration(Initialization(iterationExpression.rawSignature, null, null, null, null))
                }
                is CPPASTBinaryExpression -> {
                    println("itr") //TODO
                }
                else -> {
                    throw Exception()
                }
            }
        }
    }

    private fun solveForConditionExpression(conditionExpression: IASTExpression?, forT: For) {
        val initT = Initialization(null, null, null, null, null)
        forT.addConditionExpression(initT)
        if(conditionExpression!= null) {
            functionCallsService.getOperands(conditionExpression as CPPASTBinaryExpression, initT)
        }
    }

    private fun solveForInitialization(initializerStatement: IASTStatement?, forT: For) {
        println(initializerStatement)
        when (initializerStatement) {
            is CPPASTDeclarationStatement -> {
                (initializerStatement.declaration as CPPASTSimpleDeclaration).declarators
                    .iterator().forEachRemaining { declarator ->
                        run {
                            setInitializer(declarator, forT)
                        }
                    }
            }
            is CPPASTExpressionStatement -> {
                val inits =Initialization(null, null, null, null, null)
                val thisMethod = ASTVisitorOverride.getMethod() // check this declarations compare with inits name.
                when (initializerStatement.expression) {
                    is CPPASTBinaryExpression -> { // TODO
                        functionCallsService.getOperands(
                            initializerStatement.expression as CPPASTBinaryExpression,
                            inits
                        ) // new statement structure
                    }
                    is CPPASTUnaryExpression -> {
                        //TODO
                    }
                    else -> {
                        throw Exception()
                    }
                }
                println("we need a fix here: iuser=user.begin();") //TODO fix this
            }
            is CPPASTNullStatement -> {
                //TODO
            }
            else -> {
                throw Exception()
            }
        }
    }

    private fun setInitializer(declarator: IASTDeclarator?, forT: For) { //TODO make this a general function for every state of For{}
        val initT = Initialization(declarator!!.name.rawSignature, null, null, null, null)
        forT.addInitializer(initT)
        (declarator.initializer as CPPASTEqualsInitializer).initializerClause // fOperand1, fOperand2
        when ((declarator.initializer as CPPASTEqualsInitializer).initializerClause) {  // TODO HANDLE ASTNODESERVICE!
            is CPPASTBinaryExpression -> {
                functionCallsService.getOperands((declarator.initializer as CPPASTEqualsInitializer).initializerClause as CPPASTBinaryExpression, initT)
            }
            is CPPASTLiteralExpression -> {
                initT.add((declarator.initializer as CPPASTEqualsInitializer).initializerClause.rawSignature)
            }
            is CPPASTIdExpression -> {
                println("initr") //TODO
            }
            is CPPASTInitializerList -> {
                println("initr") //TODO
            }
            is CPPASTFunctionCallExpression -> {
                println("initr")//TODO
            }
            is CPPASTCastExpression -> {
                println("initr")//TODO
            }
            else -> {
                throw Exception()
            }
        }
    }

}