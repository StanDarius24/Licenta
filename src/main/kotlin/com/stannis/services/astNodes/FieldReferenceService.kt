package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FieldReference
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldReference

object FieldReferenceService {

    fun solveFieldReference(expression: CPPASTFieldReference, statement: Statement?) {
        val fieldRefetence = FieldReference(
            expression.fieldOwner.rawSignature,
            expression.fieldName.rawSignature
        )
        StatementMapper.addStatementToStatement(statement!!, fieldRefetence)
    }
}