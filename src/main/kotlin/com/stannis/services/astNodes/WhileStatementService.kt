package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.While
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTWhileStatement

object WhileStatementService {

    fun solveWhileStatement(data: CPPASTWhileStatement, statement: Statement?) {
        val whileStructure = While(null, null, null)

        if (data.condition != null) {
            val anonimStatement1 = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.condition as ASTNode, anonimStatement1)
            whileStructure.condition = anonimStatement1.statement
        }

        if (data.conditionDeclaration != null) {
            val anonimStatement2 = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.conditionDeclaration as ASTNode, anonimStatement2)
            whileStructure.condition2 = anonimStatement2.statement
        }

        if (data.body != null) {
            val anonimStatement3 = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.body as ASTNode, anonimStatement3)
            whileStructure.body = anonimStatement3.statement
        }

        StatementMapper.addStatementToStatement(statement!!, whileStructure)
    }
}
