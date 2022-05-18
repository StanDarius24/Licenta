package com.stannis.services.astNodes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.TypeIdExpression
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTypeIdExpression

object TypeIdExpressionService {

    fun solveTypeIdExpression(typeId: CPPASTTypeIdExpression, statement: Statement?) {
        val typeIdExpression = TypeIdExpression(typeId = null)
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(typeId.typeId as ASTNode, anonimStatement)
        typeIdExpression.typeId = anonimStatement.statement as Arguments
        StatementMapper.addStatementToStatement(statement!!, typeIdExpression)
    }
}
