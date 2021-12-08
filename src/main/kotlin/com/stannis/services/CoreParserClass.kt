package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.declSpecifier.ExpressionStatementService
import com.stannis.declSpecifier.IfStatementService
import com.stannis.declSpecifier.ReturnStatementService
import com.stannis.declSpecifier.WhileStatementService
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class CoreParserClass {
    companion object {


        fun seeCPASTCompoundStatement(data: IASTStatement, statement: Statement?) {
                println("---------")
                println(data.rawSignature)
                ASTNodeService.getInstance()
                    .solveASTNode(data as ASTNode, statement)
//                when (data) {
//                    is CPPASTDeclarationStatement -> { // ASTAttributeOwner // ASTNode TODO 2 type of classes that handle types REFACTORING NEEDED ASAP
//                        when (data.declaration) {
//                            is CPPASTSimpleDeclaration -> {
//                                DeclarationStatementParser.getInstance().declStatement(data.declaration as CPPASTSimpleDeclaration, method, null)
//                            }
//                            is CPPASTStaticAssertionDeclaration -> {
//                                println(data.declaration)
//                                //TODO
//                            }
//                            is CPPASTAliasDeclaration -> {
//                                println(data.declaration)
//                                //TODO
//                            }
//                            is CPPASTUsingDirective -> {
//                                println(data.declaration)
//                                //TODO
//                            }
//                            is CPPASTUsingDeclaration -> {
//                                println(data.declaration)
//                                //TODO
//                            }
//                            else -> {
//                                throw Exception()
//                            }
//                        }
//                    }
//                    is CPPASTExpressionStatement -> {
//                        ExpressionStatementService.getInstance().solveExpressionStatement(
//                            data,
//                            method
//                        )
//                    }
//                    is CPPASTIfStatement -> {
//                        IfStatementService.getInstance().solveIfStatement(data, method)
//                    }
//                    is CPPASTWhileStatement -> {
//                        WhileStatementService.getInstance().solveWhileStatement(data, method)
//                    }
//                    is CPPASTProblemStatement -> {
//                        println("problStatement")
//                    }
//                    is CPPASTCompoundStatement -> {
//                        data.statements.iterator()
//                            .forEachRemaining { dataStatement: IASTStatement ->
//                                seeCPASTCompoundStatement(
//                                    dataStatement,
//                                    method
//                                )
//                            }
//                    }
//                    is CPPASTReturnStatement -> {
//                        ReturnStatementService.getInstance().solveReturnStatement(data, method)
//                    }
//                    is CPPASTForStatement -> {
//                        ForBlockService.getInstance().solveForBlock(data, method)
//                    }
//                    is CPPASTSwitchStatement -> {
//                        SwitchStatementService.getInstance().solveSwitchStatement(data, method)
//                    }
//                    is CPPASTDoStatement -> {
//                        println(data) //TODO
//                    }
//                    is CPPASTContinueStatement -> {
//                        println(data) //TODO
//                    }
//                    is CPPASTBreakStatement -> {
//                        println(data) //TODO
//                    }
//                    is CPPASTRangeBasedForStatement -> {
//                        println(data) //TODO
//                    }
//                    is CPPASTGotoStatement -> {
//                        println(data) //TODO
//                    }
//                    is CPPASTLabelStatement -> {
//                        println(data)
//                    }
//                    is CPPASTNullStatement -> {
//                        println(data) //TODO
//                    }
//                    is CPPASTTryBlockStatement -> {
//                        println(data) //TODO
//                    }
//                    else -> { throw Exception() }
//                }
        }

    }
}