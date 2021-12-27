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

object WhileStatementService {

    fun solveWhileStatement(data: CPPASTWhileStatement, statement: Statement?) {
        val whileT = While(null, null, null, null)
        StatementMapper.addStatementToStatement(
            statement!!, whileT
        )
        val methodChild = MethodService.createMethod()
        whileT.addblock(methodChild)

        ASTNodeService.solveASTNode(data.condition as ASTNode, statement)

        CoreParserClass.seeCPASTCompoundStatement(data.body, methodChild)
    }
}