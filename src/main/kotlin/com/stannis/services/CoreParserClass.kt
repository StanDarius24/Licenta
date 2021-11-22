package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.declSpecifier.ExpressionStatementService
import com.stannis.declSpecifier.IfStatementService
import com.stannis.declSpecifier.ReturnStatementService
import com.stannis.declSpecifier.WhileStatementService
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class CoreParserClass {
    companion object {

        private val functionCallsService = FunctionCallsService()
        private val forBlockService = ForBlockService()
        private val declStatementParser = DeclarationStatementParser()
        private val methodService = MethodService()
        private val expressionStatementService = ExpressionStatementService()
        private val ifStatementService = IfStatementService()
        private val whileStatementService = WhileStatementService()
        private val returnStatementService = ReturnStatementService()

        fun seeCPASTCompoundStatement(data: IASTStatement, method: Method?) {
            println("---------")
            println(data.rawSignature)
            when (data) {
                is CPPASTDeclarationStatement -> {
                    declStatementParser.declStatement(data.declaration as CPPASTSimpleDeclaration, method, null)
                }
                is CPPASTExpressionStatement -> {
                    expressionStatementService.solveExpressionStatement(data, method, functionCallsService, methodService)
                }
                is CPPASTIfStatement -> {
                    ifStatementService.solveIfStatement(data, method, methodService, functionCallsService)
                }
                is CPPASTWhileStatement -> {
                    whileStatementService.solveWhileStatement(data, method, methodService, functionCallsService)
                }
                is CPPASTProblemStatement -> {
                    println("problStatement")
                }
                is CPPASTCompoundStatement -> {
                    data.statements.iterator()
                        .forEachRemaining { dataStatement: IASTStatement -> seeCPASTCompoundStatement(dataStatement, method) }
                }
                is CPPASTReturnStatement -> {
                    returnStatementService.solveReturnStatement(data, method, functionCallsService, methodService)
                }
                is CPPASTForStatement -> {
                forBlockService.solveForBlock(data, method)
                }
            }
            println()
        }

    }
}