package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.TypeId
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTypeId

object TypeIdService {
    fun solveTypeId(typeId: CPPASTTypeId, statement: Statement?) {
        val typeIdData = TypeId(declSpecifier = null, abstractDeclaration = null)
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(typeId.declSpecifier as ASTNode, anonimStatement)
        typeIdData.declSpecifier = anonimStatement.statement
        val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(typeId.abstractDeclarator as ASTNode, anonimStatement1)
        typeIdData.abstractDeclaration = anonimStatement1.statement
        StatementMapper.addStatementToStatement(statement!!, typeIdData)
    }
}
