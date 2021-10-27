package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.For
import com.stannis.dataModel.statementTypes.Initialization
import com.stannis.parser.reader.visitor.ASTVisitorOverride
import org.eclipse.cdt.core.dom.ast.IASTDeclarator
import org.eclipse.cdt.core.dom.ast.IASTExpression
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ForBlockService {

    private val functionCallsService = FunctionCallsService()
    private val methodService = MethodService()

    fun solveForBlock(data: CPPASTForStatement, method: Method?) {
        val forT = For(null, null, null, null, null)
        methodService.addStatement(method!!, forT)
        solveForInitialization(data.initializerStatement, forT)
        solveForConditionExpression(data.conditionExpression, forT)
        solveForIterationExpression(data.iterationExpression, forT)
        if(data.body != null) {
            val meth = Method(null, null, null, null, null)
            forT.addMethod(meth)
                CoreParserClass.seeCPASTCompoundStatement(data.body, meth)

        }
    }

    fun solveForIterationExpression(iterationExpression: IASTExpression?, forT: For) {
        println(iterationExpression?.rawSignature)
        val initT = Initialization(null, null, null, null)
        (iterationExpression as CPPASTExpressionList).expressions.iterator()
            .forEachRemaining { expression ->
                run {
                    functionCallsService.getOperands(expression as CPPASTBinaryExpression, initT)
                }
            }
        forT.addIteration(initT)
    }

    fun solveForConditionExpression(conditionExpression: IASTExpression?, forT: For) {
        println(conditionExpression!!.rawSignature)
        val initT = Initialization(null, null, null, null)
        forT.addConditionExpression(initT)
            functionCallsService.getOperands(conditionExpression as CPPASTBinaryExpression, initT)
    }

    fun solveForInitialization(initializerStatement: IASTStatement?, forT: For) {
        println(initializerStatement)
        ((initializerStatement as CPPASTDeclarationStatement).declaration as CPPASTSimpleDeclaration).declarators
            .iterator().forEachRemaining {
                    declarator ->
                run {
                    setInitializer(declarator, forT)
                }
            }
    }

    private fun setInitializer(declarator: IASTDeclarator?, forT: For) { //TODO make this a general function for every state of For{}
        val initT = Initialization(declarator!!.name.rawSignature, null, null, null)
        forT.addInitializer(initT)
        (declarator.initializer as CPPASTEqualsInitializer).initializerClause // fOperand1, fOperand2
        if ((declarator.initializer as CPPASTEqualsInitializer).initializerClause is CPPASTBinaryExpression) {
            functionCallsService.getOperands((declarator.initializer as CPPASTEqualsInitializer).initializerClause as CPPASTBinaryExpression, initT)
        } else if((declarator.initializer as CPPASTEqualsInitializer).initializerClause is CPPASTLiteralExpression) {
            initT.add((declarator.initializer as CPPASTEqualsInitializer).initializerClause.rawSignature)
        }
    }

}