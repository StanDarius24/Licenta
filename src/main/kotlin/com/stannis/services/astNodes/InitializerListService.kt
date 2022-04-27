package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.InitializerList
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTInitializerList

object InitializerListService {
    fun solveInitializerList(initList: CPPASTInitializerList, statement: Statement?) {
        val initializerList = InitializerList(initializers = null)
        initList.children.iterator().forEachRemaining { iastNode ->
            run {
                val anonimStatement = AnonimStatement.getNewAnonimStatement()
                ASTNodeService.solveASTNode(iastNode as ASTNode, anonimStatement)
                initializerList.addInitializers(anonimStatement.statement as Statement)
            }
        }
        StatementMapper.addStatementToStatement(statement!!, initializerList)
    }
}
