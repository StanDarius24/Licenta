package com.stannis.services

import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionCall
import com.stannis.dataModel.statementTypes.Initialization
import com.stannis.dataModel.statementTypes.Return
import org.eclipse.cdt.core.dom.ast.IASTDeclarator
import org.eclipse.cdt.core.dom.ast.IASTExpression
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause
import org.eclipse.cdt.core.dom.ast.IASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class FunctionCallsService {

    fun getFunctionCall(data: IASTDeclarator?, decl: Declaration) {
        if( data != null) {
            if( data.initializer != null) {
                data.initializer.children.iterator().forEachRemaining { datax: IASTNode ->
                    run {
                        when (datax) {
                            is CPPASTFunctionCallExpression -> {
                                val funcCall = FunctionCall(
                                    null,
                                    datax.functionNameExpression.rawSignature,
                                    null,
                                    null
                                )
                                declarationStatementForArgumentType(datax.arguments, funcCall)
                                decl.function = funcCall
                            }
                        }
                    }
                }
            }
        }
    }

    private fun declarationStatementForArgumentType(data: Array<IASTInitializerClause>?, statement: Statement?) {
        data!!.iterator().forEachRemaining {
                datax: IASTInitializerClause ->
            run {
                when (datax) {
                    is CPPASTLiteralExpression -> {
                        StatementMapper.addNameDependingOnType(statement!!, datax.rawSignature)
                    }
                    is CPPASTFunctionCallExpression -> {
                        val list = ArrayList<String>()
                        datax.arguments.iterator().forEachRemaining { data: IASTInitializerClause -> list.add(data.rawSignature) }
                        val func = FunctionCall(
                            null,
                            datax.functionNameExpression.rawSignature,
                            list, null
                        )
                        if( statement is FunctionCall) {
                            statement.add(func)
                        } else if(statement is Initialization){
                            statement.add(func)
                        }
                    }
                    is CPPASTIdExpression -> {
                        StatementMapper.addNameDependingOnType(statement!!, datax.name.rawSignature)
                    } // to add CppastBinaryExpression
                    is CPPASTBinaryExpression -> {
                        getOperands(datax, statement)
                    }
                    is CPPASTUnaryExpression -> {
                        println(datax) //TODO
                    }
                    is CPPASTArraySubscriptExpression -> {
                        println(datax) //TODO
                    }
                    is CPPASTFieldReference -> {
                        println(datax) //TODO
                    }
                    is CPPASTCastExpression -> {
                        println(datax) //TODO
                    }
                    is CPPASTTypeIdExpression -> {
                        println(datax) //TODO
                    }
                    is CPPASTConditionalExpression -> {
                        println(datax) //TODO
                    }
                    is CPPASTLambdaExpression -> {
                        println(datax) //TODO
                    }
                    is CPPASTPackExpansionExpression -> {
                        println(datax) //TODO
                    }
                    is CPPASTNewExpression -> {
                        println(datax) //TODO
                    }
                    else -> { throw Exception() }
                }
            }
        }
    }

    fun getArgumentsType(functionCall: CPPASTFunctionCallExpression, statement: Statement?) {
        declarationStatementForArgumentType(functionCall.arguments, statement)
    }

    private fun getFunctionArguments(functionCallExpression: CPPASTFunctionCallExpression, statement: Statement?) {
        println(functionCallExpression.rawSignature)
        if(functionCallExpression.functionNameExpression is CPPASTFieldReference) {
            val ffcals = FunctionCall(
                null,
                (functionCallExpression.functionNameExpression as CPPASTFieldReference).fieldName.rawSignature,
                null,
                null
            )
            if (statement is Initialization) {
                statement.name =
                    (functionCallExpression.functionNameExpression as CPPASTFieldReference).fieldOwner.rawSignature

                StatementMapper.addFunctionCallDependingOnType(statement, ffcals)
//            var returntype: String?,
//            var name: String?,
//            var parameters: ArrayList<String>?,
//            var functionCalls: ArrayList<FunctionCall>?
            } else {
                //TODO
            }
        } else {
        val functionCall = FunctionCall(null, functionCallExpression.functionNameExpression.rawSignature, null, null)
        getArgumentsType(functionCallExpression, functionCall)
        StatementMapper.addFunctionCallDependingOnType(statement!!, functionCall) }

    }

    private fun handleOperands(binaryExpression: IASTExpression, statement: Statement?) {
        when (binaryExpression) {
            is CPPASTIdExpression, is CPPASTLiteralExpression, is CPPASTUnaryExpression -> {
                println(binaryExpression.rawSignature)
                StatementMapper.addNameDependingOnType(statement!!, binaryExpression.rawSignature)
            }
            is CPPASTFunctionCallExpression -> {
                getFunctionArguments(binaryExpression, statement)
            }
            is CPPASTFieldReference -> {
                println(binaryExpression) //TODO
            }
            is CPPASTBinaryExpression -> {
                println(binaryExpression) //TODO
            }
            is CPPASTCastExpression -> {
                println(binaryExpression) //TODO
            }
            is CPPASTArraySubscriptExpression -> {
                println(binaryExpression) //TODO
            }
            is CPPASTTypeIdExpression -> {
                println(binaryExpression) //TODO
            }
            is CPPASTNewExpression -> {
                println(binaryExpression) //TODO
            }
            is CPPASTSimpleTypeConstructorExpression -> {
                println(binaryExpression) //TODO
            }
            is CPPASTConditionalExpression -> {
                println(binaryExpression) //TODO
            }
            else -> { throw Exception() }
        }
    }

    fun getOperands(binaryExpression: CPPASTBinaryExpression, statement: Statement?) { //TODO boolean operands need a fix
        while((binaryExpression.operand1 !is CPPASTIdExpression) || (binaryExpression.operand1 !is CPPASTLiteralExpression)) {
            if(binaryExpression.operand1 is CPPASTBinaryExpression) {
                getOperands(binaryExpression.operand1 as CPPASTBinaryExpression, statement)
            }
            break
        }
        while((binaryExpression.operand2 !is CPPASTIdExpression) || (binaryExpression.operand2 !is CPPASTLiteralExpression)) {
            if(binaryExpression.operand2 is CPPASTBinaryExpression) {
                getOperands(binaryExpression.operand2 as CPPASTBinaryExpression, statement)
            }
            break
        }
        if(binaryExpression.operand1 != null) {
            handleOperands(binaryExpression.operand1, statement)
        }
        if(binaryExpression.operand2 != null) {
            handleOperands(binaryExpression.operand2, statement)
        }
    }

    fun getOperandsAsFunctionCall(cppastFunctionCallExpression: CPPASTFunctionCallExpression, returnT: Return) {
        println(cppastFunctionCallExpression)
        when (cppastFunctionCallExpression.functionNameExpression) {
            is CPPASTIdExpression -> {
                val funcCall = FunctionCall(
                    null,
                    cppastFunctionCallExpression.functionNameExpression.rawSignature,
                    null, null
                )
                declarationStatementForArgumentType(cppastFunctionCallExpression.arguments, funcCall)
                returnT.add(funcCall)
            }
            is CPPASTFieldReference -> {
                val functCall = FunctionCall(
                    null,
                    (cppastFunctionCallExpression.functionNameExpression as CPPASTFieldReference).fieldOwner.rawSignature,
                    null, null
                )
                cppastFunctionCallExpression.arguments.iterator().forEachRemaining { expressions ->
                    run {
                        handleOperands(expressions as IASTExpression, functCall)
                    }
                }
                val funcCall2 = FunctionCall(
                    null,
                    (cppastFunctionCallExpression.functionNameExpression as CPPASTFieldReference).fieldName.rawSignature,
                    null, null
                )
                functCall.add(funcCall2)
                returnT.add(functCall)
            }
            is CPPASTUnaryExpression -> {
                //TODO
            }
            else -> {
                throw Exception()
            }
        }
    }

}