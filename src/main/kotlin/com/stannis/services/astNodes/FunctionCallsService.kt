package com.stannis.services.astNodes

import com.stannis.dataModel.HigherClass
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.FunctionCalls
import com.stannis.function.FunctionCallsRegistry
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
        FunctionCallsRegistry.listOfFunctionCalls.add(functionCalls)
        StatementMapper.addStatementToStatement(statement!!, functionCalls)
    }

}
