package com.stannis.services

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.SwitchStatement
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSwitchStatement

object SwitchStatementService {

    fun solveSwitchStatement(switchStatement: CPPASTSwitchStatement, statement: Statement?) {
        val data = SwitchStatement(null, null)
        val anonimStatement = AnonimStatement(null)
        ASTNodeService.solveASTNode(switchStatement.controllerExpression as ASTNode, anonimStatement)
        data.addControllerExpression(anonimStatement)
        val anonimStatement2 = AnonimStatement(null)
        ASTNodeService.solveASTNode(switchStatement.body as ASTNode, anonimStatement2)
        data.addBody(anonimStatement2)
        StatementMapper.addStatementToStatement(statement!!, data)
    }

}