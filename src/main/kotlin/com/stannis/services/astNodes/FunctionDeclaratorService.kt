package com.stannis.services.astNodes

import com.stannis.dataModel.NameInterface
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
        val functionDeclarator = FunctionDeclarator(parameter = null, name = null)
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        if (node.name != null) {
            ASTNodeService.solveASTNode(node.name as ASTNode, anonimStatement)
            functionDeclarator.name = anonimStatement.statement as NameInterface
        }
        if (node.parameters.isNotEmpty()) {
            node.parameters.iterator().forEachRemaining { param ->
                run {
                    val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
                    ASTNodeService.solveASTNode(param as ASTNode, anonimStatement1)
                    functionDeclarator.addParameter(anonimStatement1.statement as Statement)
                }
            }
        }
        StatementMapper.addStatementToStatement(statement!!, functionDeclarator)
        FunctionDeclaratorRegistry.addToList(functionDeclarator, node)
    }
}
