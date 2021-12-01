package com.stannis.services

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FieldReference
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldReference

class FieldReferenceService {
    fun solveFieldReference(expression: CPPASTFieldReference, statement: Statement?, methodService: MethodService) {
        val fieldRefetence = FieldReference(
            expression.fieldOwner.rawSignature,
            expression.fieldName.rawSignature
        )
        StatementMapper.addStatementToStatement(statement!!, fieldRefetence)
    }
}