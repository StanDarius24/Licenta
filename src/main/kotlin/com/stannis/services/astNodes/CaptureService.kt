package com.stannis.services.astNodes

import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.Capture
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCapture

object CaptureService {
    fun solveCapture(node: CPPASTCapture, statement: Statement?) {
        val capture = Capture(byReference = node.isByReference, identifier = null)
        if (node.identifier != null) {
            val anonimStatement = AnonimStatement.getNewAnonimStatement()
            ASTNodeService.solveASTNode(node.identifier as ASTNode, anonimStatement)
            capture.identifier = anonimStatement.statement as NameInterface
        }
        StatementMapper.addStatementToStatement(statement!!, capture)
    }
}