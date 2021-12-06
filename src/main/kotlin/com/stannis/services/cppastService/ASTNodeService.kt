package com.stannis.services.cppastService

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionCall
import com.stannis.dataModel.statementTypes.IdExpression
import com.stannis.services.*
import com.stannis.services.astNodes.BinaryExpressionService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ASTNodeService {

    companion object{
        private lateinit var astNodeService: ASTNodeService

        fun getInstance(): ASTNodeService {
            if(!::astNodeService.isInitialized) {
                astNodeService = ASTNodeService()
            }
            return astNodeService
        }
    }

    fun solveASTNode(node: ASTNode): Statement? {
        lateinit var statement: Statement
        when (node) {
            is CPPASTBinaryExpression -> {
                statement = BinaryExpressionService.getInstance().solveBinaryExpressionService(
                    node
                )
                println(node)
            }
            is CPPASTFunctionCallExpression -> {
                println(node)
//                functionCallsService.functionCallExprSolver(node, method, functionCallsService, methodService)
            }
            is CPPASTUnaryExpression -> {
                println(node)
//                unaryExpressionService.solveUneryExpression(node, method, methodService)
            }
            is CPPASTFieldReference -> {
                println(node)
//                fieldReferenceService.solveFieldReference(node, method, methodService)
            }
            is CPPASTDeleteExpression -> {
                println(node)
//                deleteExpressionService.solveDeleteExpression(node, method, methodService)
            }
            is CPPASTCastExpression -> {
                println(node)
//                castExpressionService.solveCastExpression(node, method!!)
            }
            is CPPASTLiteralExpression -> {
                println(node)
//                literalExpressionService.solveLiteralExpression(node, method, methodService)
            }
            is CPPASTIdExpression -> {
                statement = IdExpression(node.rawSignature)
                println(node)
            }
            is CPPASTNewExpression -> {
                val funcCall = FunctionCall(
                    null,
                    (node.typeId.declSpecifier as CPPASTNamedTypeSpecifier).name.rawSignature,
                    null,null,null
                )
                FunctionCallsService.getInstance()
                    .declarationStatementForArgumentType(
                        (node.initializer as CPPASTConstructorInitializer).arguments,
                        funcCall
                    )
                StatementMapper.addFunctionCallDependingOnType(statement!!, funcCall)
            }
            is CPPASTFunctionCallExpression -> {
                val funcCall = FunctionCall(
                    null,
                    node.functionNameExpression.rawSignature,
                    null,
                    null,
                    null
                )
                FunctionCallsService.getInstance().declarationStatementForArgumentType(node.arguments, funcCall)
            }
            else -> throw Exception()
        }

        return statement
    }

}