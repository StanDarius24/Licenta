package com.stannis.declSpecifier

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.While
import com.stannis.services.CoreParserClass
import com.stannis.services.MethodService
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTWhileStatement

class WhileStatementService {

    companion object{
        private lateinit var whileStatementService: WhileStatementService

        fun getInstance(): WhileStatementService{
            if(!::whileStatementService.isInitialized) {
                whileStatementService = WhileStatementService()
            }
            return whileStatementService
        }
    }

    fun solveWhileStatement(data: CPPASTWhileStatement, statement: Statement?) {
        val whileT = While(null, null, null, null)
        StatementMapper.addStatementToStatement(
            statement!!, whileT
        )
        val methodChild = MethodService.getInstance().createMethod()
        whileT.addblock(methodChild)

        ASTNodeService.getInstance()
            .solveASTNode(data.condition as ASTNode, statement)

        CoreParserClass.seeCPASTCompoundStatement(data.body, methodChild)
    }
}