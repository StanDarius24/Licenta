package com.stannis.services

import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.core.dom.ast.IASTDeclarator
import org.eclipse.cdt.core.dom.ast.IASTExpression
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause
import org.eclipse.cdt.core.dom.ast.IASTNode
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object FunctionCallsService {

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
                val anonimStatement = AnonimStatement(null)
                ASTNodeService.solveASTNode(datax as ASTNode, anonimStatement)
                StatementMapper.addStatementToStatement(statement!!, anonimStatement.statement as Statement)
            }
        }
    }

    fun getArgumentsType(functionCall: CPPASTFunctionCallExpression, statement: Statement?) {
        declarationStatementForArgumentType(functionCall.arguments, statement)
    }

    private fun handleOperands(binaryExpression: IASTExpression, statement: Statement?) {
        ASTNodeService.solveASTNode(binaryExpression as ASTNode, statement)
//        when (binaryExpression) {
//            is CPPASTIdExpression, is CPPASTLiteralExpression, is CPPASTUnaryExpression -> {
//                println(binaryExpression.rawSignature)
//                StatementMapper.addNameDependingOnType(statement!!, binaryExpression.rawSignature)
//            }
//            is CPPASTFunctionCallExpression -> { // TODO HANDLE ASTNODESERVICE!
//                getFunctionArguments(binaryExpression, statement)
//            }
//            is CPPASTFieldReference -> {
//                FieldReferenceService.solveFieldReference(
//                    binaryExpression,
//                    statement
//                )
//            }
//            is CPPASTBinaryExpression -> {
//                getOperands(binaryExpression, statement)
//            }
//            is CPPASTCastExpression -> {
//                CastExpressionService.solveCastExpression(
//                    binaryExpression,
//                    statement!!
//                )
//            }
//            is CPPASTArraySubscriptExpression -> {
//                println(binaryExpression) //TODO
//            }
//            is CPPASTTypeIdExpression -> {
//                println(binaryExpression) //TODO
//            }
//            is CPPASTNewExpression -> {
//                val funcCall = FunctionCall(
//                    null,
//                    (binaryExpression.typeId.declSpecifier as CPPASTNamedTypeSpecifier).name.rawSignature,
//                    null,null,null
//                )
//                declarationStatementForArgumentType((binaryExpression.initializer as CPPASTConstructorInitializer).arguments, funcCall)
//                StatementMapper.addFunctionCallDependingOnType(statement!!, funcCall)
//            }
//            is CPPASTSimpleTypeConstructorExpression -> {
//                SimpleTypeConstructorExpressionService.solveTypeConstructorExpre(
//                    binaryExpression,
//                    statement!!
//                )
//            }
//            is CPPASTConditionalExpression -> {
//                println(binaryExpression) //TODO
//            }
//            else -> { throw Exception() }
//        }
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

}