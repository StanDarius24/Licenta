package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.FieldReference
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldReference

object FieldReferenceService {

    fun solveFieldReference(expression: CPPASTFieldReference, statement: Statement?) {
        val fieldReference = FieldReference(null, null)
        val anonimStatement1 = AnonimStatement(null)
        ASTNodeService.solveASTNode(expression.fieldOwner as ASTNode, anonimStatement1)
        fieldReference.fieldOwner = anonimStatement1.statement
        val anonimStatement2 = AnonimStatement(null)
        ASTNodeService.solveASTNode(expression.fieldName as ASTNode, anonimStatement2)
        fieldReference.fieldName = anonimStatement2.statement
        StatementMapper.addStatementToStatement(statement!!, fieldReference)
    }
}
