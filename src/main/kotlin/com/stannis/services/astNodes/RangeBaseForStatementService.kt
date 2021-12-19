package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.RangeBasedForStatement
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTRangeBasedForStatement

object RangeBaseForStatementService {

    fun solveRangeBaseForStatement(rangebase: CPPASTRangeBasedForStatement, statement: Statement?) {
        val range = RangeBasedForStatement(null, null, null)
        val anonimStatement1 = AnonimStatement(null)
        ASTNodeService.getInstance()
            .solveASTNode(rangebase.declaration as ASTNode, anonimStatement1)
        range.addDeclaration(anonimStatement1)
        val anonimStatement2 = AnonimStatement(null)
        ASTNodeService.getInstance()
            .solveASTNode(rangebase.initializerClause as ASTNode, anonimStatement2)
        range.addInitClause(anonimStatement2)
        val anonimStatement3 = AnonimStatement(null)
        ASTNodeService.getInstance()
            .solveASTNode(rangebase.body as ASTNode, anonimStatement3)
        range.addBody(anonimStatement3)
        StatementMapper.addStatementToStatement(statement!!, range)
    }
}