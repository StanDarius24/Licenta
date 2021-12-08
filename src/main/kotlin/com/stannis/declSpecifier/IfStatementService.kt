package com.stannis.declSpecifier

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.If
import com.stannis.services.CoreParserClass
import com.stannis.services.MethodService
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class IfStatementService {

    companion object{
        private lateinit var ifStatementService: IfStatementService

        fun getInstance(): IfStatementService {
            if(!::ifStatementService.isInitialized) {
                ifStatementService = IfStatementService()
            }
            return ifStatementService
        }
    }

    fun solveIfStatement(data: CPPASTIfStatement,  statement: Statement?) {
        println("ifStatement")
        val ifT = If(null, null, null, null, null)
        StatementMapper.addStatementToStatement(statement!!, ifT)
        if (data.conditionExpression != null) {
            ASTNodeService.getInstance()
                .solveASTNode(
                    data.conditionExpression as ASTNode,
                    ifT
                )
        }
        val ifBlock = MethodService.getInstance().createMethod()
        ifT.addIfBlock(ifBlock)
        val elseBlock = MethodService.getInstance().createMethod()
        ifT.addElseBlock(elseBlock)
        if (data.thenClause != null)
            CoreParserClass.seeCPASTCompoundStatement(data.thenClause, ifBlock)
        if (data.elseClause != null)
            CoreParserClass.seeCPASTCompoundStatement(data.elseClause, elseBlock)
    }
}