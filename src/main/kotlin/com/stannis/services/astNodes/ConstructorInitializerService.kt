package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ConstructorInitializer
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConstructorInitializer

object ConstructorInitializerService {

    fun solveConstructorInitializer(constructorInitializer: CPPASTConstructorInitializer, statement: Statement?) {
        val constrInit = ConstructorInitializer(null)
        constructorInitializer.arguments
            .iterator().forEachRemaining { arg ->
                run {
                    ASTNodeService.getInstance()
                        .solveASTNode(arg as ASTNode, constrInit)
                }
            }
        StatementMapper.addStatementToStatement(statement!!, constrInit)
    }
}