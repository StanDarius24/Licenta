package com.stannis.services.astNodes

import com.stannis.dataModel.HigherClass
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.FunctionCalls
import com.stannis.function.FunctionDefinitionRegistry
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object FunctionCallsService {

    fun solveFunctionCalls(node: CPPASTFunctionCallExpression, statement: Statement?) {
        val functionCalls = FunctionCalls(name = null, arguments = null)
        val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(node.functionNameExpression as ASTNode, anonimStatement1)
        functionCalls.name = anonimStatement1.statement as HigherClass
        node.arguments.iterator().forEachRemaining { argument ->
            run {
                val anonimStatement = AnonimStatement.getNewAnonimStatement()
                ASTNodeService.solveASTNode(argument as ASTNode, anonimStatement)
                functionCalls.addArgument(anonimStatement.statement as Statement)
            }
        }
        findParent(functionCalls, node)
        StatementMapper.addStatementToStatement(statement!!, functionCalls)
    }

    private fun findParent(functionCalls: FunctionCalls, node: ASTNode) {
        var parent = node
        while (!(parent is CPPASTFunctionDefinition) && !(parent is CPPASTTranslationUnit)) {
            parent = parent.parent as ASTNode
        }
        if(!checkIfClassDefinition(parent)) {
            if (parent is CPPASTFunctionDefinition) {
                FunctionDefinitionRegistry.addFunctionCallToFunctionDefinition(functionCalls, parent)
            }
        }
    }

    private fun checkIfClassDefinition(parent: ASTNode): Boolean {
        var newparent = parent.parent
        while (newparent != null && !(newparent is CPPASTCompositeTypeSpecifier) && !(newparent is CPPASTTranslationUnit)) {
            newparent = newparent.parent
        }
        return newparent is CPPASTCompositeTypeSpecifier
    }


}
