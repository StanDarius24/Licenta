package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*
import com.stannis.function.FunctionDefinitionRegistry
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.core.dom.ast.IASTExpression
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object FunctionCallsService {

    fun solveFunctionCalls(node: CPPASTFunctionCallExpression, statement: Statement?) {
        val functionCalls = FunctionCalls(null, null)
        val anonimStatement1 = AnonimStatement(null)
        ASTNodeService.solveASTNode(node.functionNameExpression as ASTNode, anonimStatement1)
        functionCalls.name = anonimStatement1.statement
        node.arguments.iterator().forEachRemaining { argument ->
            run {
                val anonimStatement = AnonimStatement(null)
                ASTNodeService.solveASTNode(argument as ASTNode, anonimStatement)
                functionCalls.addArgument(anonimStatement.statement as Statement)
            }
        }
        findParent(functionCalls, node)
        StatementMapper.addStatementToStatement(statement!!, functionCalls)
    }

    private fun findParent(functionCalls: FunctionCalls, node: ASTNode) {
        println()
        var parent = node
        while (!(parent is CPPASTFunctionDefinition) && !(parent is CPPASTTranslationUnit)) {
            parent = parent.parent as ASTNode
        }
        if (parent is CPPASTFunctionDefinition) {
            FunctionDefinitionRegistry.addFunctionCallToFunctionDefinition(functionCalls, parent)
        }
    }

    private fun declarationStatementForArgumentType(
        data: Array<IASTInitializerClause>?,
        statement: Statement?
    ) {
        data!!.iterator().forEachRemaining { datax: IASTInitializerClause ->
            run {
                val anonimStatement = AnonimStatement(null)
                ASTNodeService.solveASTNode(datax as ASTNode, anonimStatement)
                StatementMapper.addStatementToStatement(
                    statement!!,
                    anonimStatement.statement as Statement
                )
            }
        }
    }

    fun getArgumentsType(functionCall: CPPASTFunctionCallExpression, statement: Statement?) {
        declarationStatementForArgumentType(functionCall.arguments, statement)
    }

    private fun handleOperands(binaryExpression: IASTExpression, statement: Statement?) {
        ASTNodeService.solveASTNode(binaryExpression as ASTNode, statement)
    }

    fun getOperands(
        binaryExpression: CPPASTBinaryExpression,
        statement: Statement?
    ) { // TODO boolean operands need a fix
        while ((binaryExpression.operand1 !is CPPASTIdExpression) ||
            (binaryExpression.operand1 !is CPPASTLiteralExpression)) {
            if (binaryExpression.operand1 is CPPASTBinaryExpression) {
                getOperands(binaryExpression.operand1 as CPPASTBinaryExpression, statement)
            }
            break
        }
        while ((binaryExpression.operand2 !is CPPASTIdExpression) ||
            (binaryExpression.operand2 !is CPPASTLiteralExpression)) {
            if (binaryExpression.operand2 is CPPASTBinaryExpression) {
                getOperands(binaryExpression.operand2 as CPPASTBinaryExpression, statement)
            }
            break
        }
        if (binaryExpression.operand1 != null) {
            handleOperands(binaryExpression.operand1, statement)
        }
        if (binaryExpression.operand2 != null) {
            handleOperands(binaryExpression.operand2, statement)
        }
    }
}
