package com.stannis.services

import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*
import com.stannis.services.astNodes.*
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.core.dom.ast.IASTDeclarator
import org.eclipse.cdt.core.dom.ast.IASTExpression
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause
import org.eclipse.cdt.core.dom.ast.IASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class FunctionCallsService {

    companion object {

        private lateinit var functionCall: FunctionCallsService

        fun getInstance(): FunctionCallsService {
            if (!::functionCall.isInitialized) {
                functionCall = FunctionCallsService()
            }
            return functionCall
        }
    }

    private var fieldReferenceService = FieldReferenceService() //CDI here
    private var methodService = MethodService()
    private var typeIdExpressionService = TypeIdExpressionService()
    private var castExpressionService = CastExpressionService()
    private var arraySubscriptExpressionService = ArraySubscriptExpressionService()
    private var conditionalExpressionService = ConditionalExpressionService()
    private var simpleTypeConstructorExpressionService = SimpleTypeConstructorExpressionService()

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

    fun setFunctionCallExpression(cppastFunctionCallExpression: CPPASTFunctionCallExpression, statement: Statement?) {
        println(cppastFunctionCallExpression)
        val functionCall = FunctionCall(
            null,
            cppastFunctionCallExpression.functionNameExpression.rawSignature,
            null,
            null,
            null
        )
        declarationStatementForArgumentType(cppastFunctionCallExpression.arguments, functionCall)
        StatementMapper.addStatementToStatement(statement!!, functionCall)
    }

    fun declarationStatementForArgumentType(data: Array<IASTInitializerClause>?, statement: Statement?) {
        data!!.iterator().forEachRemaining {
                datax: IASTInitializerClause ->
            run {
                when (datax) {
                    is CPPASTLiteralExpression -> { // TODO HANDLE ASTNODESERVICE!
                        StatementMapper.addNameDependingOnType(statement!!, datax.rawSignature)
                    }
                    is CPPASTFunctionCallExpression -> {
                        val list = ArrayList<String>()
                        datax.arguments.iterator().forEachRemaining { data: IASTInitializerClause -> list.add(data.rawSignature) }
                        val func = FunctionCall(
                            null,
                            datax.functionNameExpression.rawSignature,
                            list, null, null
                        )
                        if( statement is FunctionCall) {
                            statement.add(func)
                        } else if(statement is Initialization){
                            statement.add(func)
                        }
                    }
                    is CPPASTIdExpression -> {
                        StatementMapper.addNameDependingOnType(statement!!, datax.name.rawSignature)
                    }
                    is CPPASTBinaryExpression -> {
                        getOperands(datax, statement)
                    }
                    is CPPASTUnaryExpression -> {
                        if(statement is FunctionCall) {
                            statement.addParameters(datax.operand.rawSignature)
                        }
                    }
                    is CPPASTArraySubscriptExpression -> {
                        arraySubscriptExpressionService.solveArraySubscript(
                            datax,
                            statement!!
                        )
                    }
                    is CPPASTFieldReference -> {
                        fieldReferenceService.solveFieldReference(
                            datax,
                            statement
                        )
                    }
                    is CPPASTCastExpression -> {
                        castExpressionService.solveCastExpression(
                            datax,
                            statement!!
                        )
                    }
                    is CPPASTTypeIdExpression -> {
                        typeIdExpressionService.solveTypeIdExpression(
                            statement!!,
                            datax
                            )
                    }
                    is CPPASTConditionalExpression -> {
                        conditionalExpressionService.solveConditionalExpression(
                            datax,
                            statement!!
                        )

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
                null, null
            )
            when (statement) {
                is Initialization -> {
                    statement.name =
                        (functionCallExpression.functionNameExpression as CPPASTFieldReference).fieldOwner.rawSignature

                    StatementMapper.addFunctionCallDependingOnType(statement, ffcals)
        //            var returntype: String?,
        //            var name: String?,
        //            var parameters: ArrayList<String>?,
        //            var functionCalls: ArrayList<FunctionCall>?
                }
                is If -> {
                    statement.add(ffcals)
                }
                is FunctionCall -> {
                    statement.add(ffcals)
                }
                else -> {
                    //TODO
                    throw Exception()
                }
            }
        } else {
        val functionCall = FunctionCall(null, functionCallExpression.functionNameExpression.rawSignature, null, null, null)
        getArgumentsType(functionCallExpression, functionCall)
        StatementMapper.addFunctionCallDependingOnType(statement!!, functionCall) }

    }

    private fun handleOperands(binaryExpression: IASTExpression, statement: Statement?) {
        when (binaryExpression) {
            is CPPASTIdExpression, is CPPASTLiteralExpression, is CPPASTUnaryExpression -> {
                println(binaryExpression.rawSignature)
                StatementMapper.addNameDependingOnType(statement!!, binaryExpression.rawSignature)
            }
            is CPPASTFunctionCallExpression -> { // TODO HANDLE ASTNODESERVICE!
                getFunctionArguments(binaryExpression, statement)
            }
            is CPPASTFieldReference -> {
                fieldReferenceService.solveFieldReference(
                    binaryExpression,
                    statement
                )
            }
            is CPPASTBinaryExpression -> {
                getOperands(binaryExpression, statement)
            }
            is CPPASTCastExpression -> {
                castExpressionService.solveCastExpression(
                    binaryExpression,
                    statement!!
                )
            }
            is CPPASTArraySubscriptExpression -> {
                println(binaryExpression) //TODO
            }
            is CPPASTTypeIdExpression -> {
                println(binaryExpression) //TODO
            }
            is CPPASTNewExpression -> {
                val funcCall = FunctionCall(
                    null,
                    (binaryExpression.typeId.declSpecifier as CPPASTNamedTypeSpecifier).name.rawSignature,
                    null,null,null
                )
                declarationStatementForArgumentType((binaryExpression.initializer as CPPASTConstructorInitializer).arguments, funcCall)
                StatementMapper.addFunctionCallDependingOnType(statement!!, funcCall)
            }
            is CPPASTSimpleTypeConstructorExpression -> {
                simpleTypeConstructorExpressionService.solveTypeConstructorExpre(
                    binaryExpression,
                    statement!!
                )
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
                    null, null, null
                )
                declarationStatementForArgumentType(cppastFunctionCallExpression.arguments, funcCall)
                returnT.add(funcCall)
            }
            is CPPASTFieldReference -> {
                val functCall = FunctionCall(
                    null,
                    (cppastFunctionCallExpression.functionNameExpression as CPPASTFieldReference).fieldOwner.rawSignature,
                    null, null, null
                )
                cppastFunctionCallExpression.arguments.iterator().forEachRemaining { expressions ->
                    run {
                        handleOperands(expressions as IASTExpression, functCall)
                    }
                }
                val funcCall2 = FunctionCall(
                    null,
                    (cppastFunctionCallExpression.functionNameExpression as CPPASTFieldReference).fieldName.rawSignature,
                    null, null, null
                )
                functCall.add(funcCall2)
                returnT.add(functCall)
            }
            is CPPASTUnaryExpression -> {
                println(cppastFunctionCallExpression.functionNameExpression)
                //TODO
            }
            else -> {
                throw Exception()
            }
        }
    }

    fun solve(node: CPPASTFunctionCallExpression, statement: Statement) {
        println(node)
    }

}