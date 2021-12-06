package com.stannis.services

import com.google.inject.Inject
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
        private val switchStatementService = SwitchStatementService()

        fun seeCPASTCompoundStatement(data: IASTStatement, method: Method?) {
                println("---------")
                println(data.rawSignature)
                when (data) {
                    is CPPASTDeclarationStatement -> { // ASTAttributeOwner // ASTNode TODO 2 type of classes that handle types REFACTORING NEEDED ASAP
                        when (data.declaration) {
                            is CPPASTSimpleDeclaration -> {
                                declStatementParser.declStatement(data.declaration as CPPASTSimpleDeclaration, method, null)
                            }
                            is CPPASTStaticAssertionDeclaration -> {
                                println(data.declaration)
                                //TODO
                            }
                            is CPPASTAliasDeclaration -> {
                                println(data.declaration)
                                //TODO
                            }
                            is CPPASTUsingDirective -> {
                                println(data.declaration)
                                //TODO
                            }
                            is CPPASTUsingDeclaration -> {
                                println(data.declaration)
                                //TODO
                            }
                            else -> {
                                throw Exception()
                            }
                        }
                    }
                    is CPPASTExpressionStatement -> {
                        expressionStatementService.solveExpressionStatement(
                            data,
                            method,
                            functionCallsService,
                            methodService
                        )
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
                            .forEachRemaining { dataStatement: IASTStatement ->
                                seeCPASTCompoundStatement(
                                    dataStatement,
                                    method
                                )
                            }
                    }
                    is CPPASTReturnStatement -> {
                        returnStatementService.solveReturnStatement(data, method, functionCallsService, methodService)
                    }
                    is CPPASTForStatement -> {
                        forBlockService.solveForBlock(data, method)
                    }
                    is CPPASTSwitchStatement -> {
                        switchStatementService.solveSwitchStatement(data, method)
                    }
                    is CPPASTDoStatement -> {
                        println(data) //TODO
                    }
                    is CPPASTContinueStatement -> {
                        println(data) //TODO
                    }
                    is CPPASTBreakStatement -> {
                        println(data) //TODO
                    }
                    is CPPASTRangeBasedForStatement -> {
                        println(data) //TODO
                    }
                    is CPPASTGotoStatement -> {
                        println(data) //TODO
                    }
                    is CPPASTLabelStatement -> {
                        println(data)
                    }
                    is CPPASTNullStatement -> {
                        println(data) //TODO
                    }
                    is CPPASTTryBlockStatement -> {
                        println(data) //TODO
                    }
                    else -> { throw Exception() }
                }
                println()
        }

    }
}