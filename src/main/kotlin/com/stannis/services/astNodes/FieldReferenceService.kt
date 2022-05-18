package com.stannis.services.astNodes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.FieldReference
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldReference

object FieldReferenceService {

    fun solveFieldReference(expression: CPPASTFieldReference, statement: Statement?) {
        val fieldReference = FieldReference(fieldName = null, fieldOwner = null)
        val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(expression.fieldOwner as ASTNode, anonimStatement1)
        fieldReference.fieldOwner = anonimStatement1.statement as Arguments
        val anonimStatement2 = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(expression.fieldName as ASTNode, anonimStatement2)
        fieldReference.fieldName = anonimStatement2.statement as NameInterface
        StatementMapper.addStatementToStatement(statement!!, fieldReference)
    }
}
