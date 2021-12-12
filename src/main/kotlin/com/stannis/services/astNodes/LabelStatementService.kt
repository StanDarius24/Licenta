package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.LabelStatement
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLabelStatement

class LabelStatementService {

    companion object{
        private lateinit var labelStatementService: LabelStatementService

        fun getInstance(): LabelStatementService {
            if(!::labelStatementService.isInitialized) {
                labelStatementService = LabelStatementService()
            }
            return labelStatementService
        }
    }

    fun solveLabelStatement(labelCppast: CPPASTLabelStatement, statement: Statement?) {
        val labelStatement = LabelStatement(labelCppast.name.rawSignature, null)
        val anonimStatement = AnonimStatement(null)
        ASTNodeService.getInstance()
            .solveASTNode(labelCppast.nestedStatement as ASTNode, anonimStatement)
        labelStatement.addExpression(anonimStatement)
        StatementMapper.addStatementToStatement(statement!!, labelStatement)
    }
}