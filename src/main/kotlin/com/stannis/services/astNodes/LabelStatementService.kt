package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.LabelStatement
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLabelStatement

object LabelStatementService {

    fun solveLabelStatement(labelCppast: CPPASTLabelStatement, statement: Statement?) {
        val labelStatement = LabelStatement(name = labelCppast.name.rawSignature, expressions = null)
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(labelCppast.nestedStatement as ASTNode, anonimStatement)
        labelStatement.addExpression(anonimStatement.statement as Statement)
        StatementMapper.addStatementToStatement(statement!!, labelStatement)
    }
}
