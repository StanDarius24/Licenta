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
        val range = RangeBasedForStatement(declaration = null, initClause = null, body = null)
        val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(rangebase.declaration as ASTNode, anonimStatement1)
        range.addDeclaration(anonimStatement1.statement as Statement)
        val anonimStatement2 = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(rangebase.initializerClause as ASTNode, anonimStatement2)
        range.addInitClause(anonimStatement2.statement as Statement)
        val anonimStatement3 = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(rangebase.body as ASTNode, anonimStatement3)
        range.addBody(anonimStatement3.statement as Statement)
        StatementMapper.addStatementToStatement(statement!!, range)
    }
}
