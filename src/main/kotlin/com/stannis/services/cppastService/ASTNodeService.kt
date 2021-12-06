package com.stannis.services.cppastService

import com.google.inject.Inject
import com.stannis.dataModel.Statement
import com.stannis.services.*
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ASTNodeService {

    private var binaryExpressionService = BinaryExpressionService()

    fun solveASTNode(node: ASTNode): Statement? {
        lateinit var statement: Statement
        when (node) {
            is CPPASTBinaryExpression -> {
                statement = binaryExpressionService.solveBinaryExpressionService(
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
            else -> throw Exception()
        }

        return null
    }

}