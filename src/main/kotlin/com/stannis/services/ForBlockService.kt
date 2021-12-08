package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.For
import com.stannis.dataModel.statementTypes.Initialization
import com.stannis.parser.reader.visitor.ASTVisitorOverride
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.core.dom.ast.IASTDeclarator
import org.eclipse.cdt.core.dom.ast.IASTExpression
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ForBlockService {

    companion object{
        private lateinit var forBlockService: ForBlockService

        fun getInstance(): ForBlockService {
            if(!::forBlockService.isInitialized) {
                forBlockService = ForBlockService()
            }
            return forBlockService
        }
    }

    fun solveForBlock(data: CPPASTForStatement, statement: Statement) {
        val forT = For(null, null, null, null, null)
        StatementMapper.addStatementToStatement(
            statement, forT
        )
        solveForInitialization(data.initializerStatement, forT)
        solveForConditionExpression(data.conditionExpression, forT)
        solveForIterationExpression(data.iterationExpression, forT)
        if(data.body != null) {
            val meth = MethodService.getInstance().createMethod()
            forT.addMethod(meth)
                CoreParserClass.seeCPASTCompoundStatement(data.body, meth)
        }
    }

    private fun solveForIterationExpression(iterationExpression: IASTExpression?, forT: For) {
        if( iterationExpression != null) {
            println(iterationExpression.rawSignature)
            ASTNodeService.getInstance()
                .solveASTNode(iterationExpression as ASTNode, forT)
        }
    }


    private fun solveForConditionExpression(conditionExpression: IASTExpression?, forT: For) {
        val initT = Initialization(null, null, null, null, null)
        forT.addConditionExpression(initT)
        if(conditionExpression != null) {
            ASTNodeService.getInstance()
                .solveASTNode(conditionExpression as ASTNode, initT)
        }
    }

    private fun solveForInitialization(initializerStatement: IASTStatement?, forT: For) {
        when (initializerStatement) {
            is CPPASTDeclarationStatement -> {
                (initializerStatement.declaration as CPPASTSimpleDeclaration).declarators
                    .iterator().forEachRemaining { declarator ->
                        run {
                            setInitializer(declarator, forT)
                        }
                    }
            }
            is CPPASTExpressionStatement -> {
                val inits =Initialization(null, null, null, null, null)
                val thisMethod = ASTVisitorOverride.getMethod() // check this declarations compare with inits name.
                ASTNodeService.getInstance()
                    .solveASTNode(initializerStatement.expression as ASTNode, inits)

                    when (initializerStatement.expression) {
                        is CPPASTBinaryExpression -> { // TODO
                            FunctionCallsService.getInstance().getOperands(
                                initializerStatement.expression as CPPASTBinaryExpression,
                                inits
                            ) // new statement structure
                        }
                        is CPPASTUnaryExpression -> {
                            //TODO
                        }
                        else -> {
                            throw Exception()
                        }
                    }
            }
            is CPPASTNullStatement -> {
                //TODO
            }
            else -> {
                throw Exception()
            }
        }
    }

    private fun setInitializer(declarator: IASTDeclarator?, forT: For) { //TODO make this a general function for every state of For{}
        val initT = Initialization(declarator!!.name.rawSignature, null, null, null, null)
        forT.addInitializer(initT)

        ASTNodeService.getInstance()
            .solveASTNode(declarator as ASTNode, initT)

//        when ((declarator.initializer as CPPASTEqualsInitializer).initializerClause) {  // TODO HANDLE ASTNODESERVICE!
//            is CPPASTBinaryExpression -> {
//                FunctionCallsService.getInstance().getOperands((declarator.initializer as CPPASTEqualsInitializer).initializerClause as CPPASTBinaryExpression, initT)
//            }
//            is CPPASTLiteralExpression -> {
//                initT.add((declarator.initializer as CPPASTEqualsInitializer).initializerClause.rawSignature)
//            }
//            is CPPASTIdExpression -> {
//                println("initr") //TODO
//            }
//            is CPPASTInitializerList -> {
//                println("initr") //TODO
//            }
//            is CPPASTFunctionCallExpression -> {
//                println("initr")//TODO
//            }
//            is CPPASTCastExpression -> {
//                println("initr")//TODO
//            }
//            else -> {
//                throw Exception()
//            }
//        }
    }

}