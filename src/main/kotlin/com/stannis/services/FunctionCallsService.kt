package com.stannis.services

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionCall
import com.stannis.dataModel.statementTypes.Initialization
import org.eclipse.cdt.core.dom.ast.IASTExpression
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class FunctionCallsService {

    private fun declarationStatementForArgumentType(data: Array<IASTInitializerClause>?, statement: Statement?) {
        data!!.iterator().forEachRemaining {
                datax: IASTInitializerClause ->
            run {
                when (datax) {
                    is CPPASTLiteralExpression -> {
                        statement!!.add(datax.rawSignature)
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
                        statement!!.add(datax.name.rawSignature)
                    } // to add CppastBinaryExpression
                    is CPPASTBinaryExpression -> {
                        getOperands(datax, statement)
                    }
                }
            }
        }
    }

    private fun getArgumentsType(functionCall: CPPASTFunctionCallExpression, statement: Statement?) {
        declarationStatementForArgumentType(functionCall.arguments, statement)

    }

    private fun getFunctionArguments(functionCallExpression: CPPASTFunctionCallExpression, statement: Statement?) {
        println(functionCallExpression.rawSignature)

        val functionCall = FunctionCall(null, functionCallExpression.functionNameExpression.rawSignature, null, null)
        getArgumentsType(functionCallExpression, functionCall)
        statement!!.add(functionCall)
        (functionCallExpression.functionNameExpression as CPPASTIdExpression).name.rawSignature //// function name
        functionCallExpression.arguments // array of arguments
        declarationStatementForArgumentType(functionCallExpression.arguments, statement) //TODO functioncall multiple parameter
        functionCallExpression.evaluation
    }

    private fun handleOperands(binaryExpression: IASTExpression, statement: Statement?) {
        if((binaryExpression is CPPASTIdExpression) || (binaryExpression is CPPASTLiteralExpression) || (binaryExpression is CPPASTUnaryExpression)) {
            println(binaryExpression.rawSignature)
            statement!!.add(binaryExpression.rawSignature)
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