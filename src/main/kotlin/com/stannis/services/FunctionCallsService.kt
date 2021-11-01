package com.stannis.services

import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionCall
import com.stannis.dataModel.statementTypes.Initialization
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
                }
            }
        }
    }

    fun getArgumentsType(functionCall: CPPASTFunctionCallExpression, statement: Statement?) {
        declarationStatementForArgumentType(functionCall.arguments, statement)
    }

    private fun getFunctionArguments(functionCallExpression: CPPASTFunctionCallExpression, statement: Statement?) {
        println(functionCallExpression.rawSignature)
        val functionCall = FunctionCall(null, functionCallExpression.functionNameExpression.rawSignature, null, null)
        getArgumentsType(functionCallExpression, functionCall)
        StatementMapper.addFunctionCallDependingOnType(statement!!, functionCall)
    }

    private fun handleOperands(binaryExpression: IASTExpression, statement: Statement?) {
        if((binaryExpression is CPPASTIdExpression) || (binaryExpression is CPPASTLiteralExpression) || (binaryExpression is CPPASTUnaryExpression)) {
            println(binaryExpression.rawSignature)
            StatementMapper.addNameDependingOnType(statement!!, binaryExpression.rawSignature)
        } else if(binaryExpression is CPPASTFunctionCallExpression) {
            getFunctionArguments(binaryExpression, statement)
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
        handleOperands(binaryExpression.operand1, statement)
        handleOperands(binaryExpression.operand2, statement)
    }

}