package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.FunctionDeclarator
import com.stannis.function.FunctionDeclaratorRegistry
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator

object FunctionDeclaratorService {
    fun solveFunctionDeclarator(node: CPPASTFunctionDeclarator, statement: Statement?) {
        val functionDeclarator = FunctionDeclarator(null, null)
        val anonimStatement = AnonimStatement(null)
        if (node.name != null) {
            ASTNodeService.solveASTNode(node.name as ASTNode, anonimStatement)
            functionDeclarator.name = anonimStatement.statement
        }
        if(node.parameters.size > 0) {
            node.parameters.iterator().forEachRemaining { param -> run {
                val anonimStatement1 = AnonimStatement(null)
                ASTNodeService.solveASTNode(param as ASTNode, anonimStatement1)
                functionDeclarator.addParameter(anonimStatement1.statement as Statement)
                }
            }
        }
        StatementMapper.addStatementToStatement(statement!!, functionDeclarator)
        FunctionDeclaratorRegistry.addToList(functionDeclarator)
    }
}