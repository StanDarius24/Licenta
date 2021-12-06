package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FieldReference
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldReference

class FieldReferenceService {

    companion object {
        private lateinit var fieldReferenceService: FieldReferenceService

        fun getInstance(): FieldReferenceService {
            if(!::fieldReferenceService.isInitialized) {
                fieldReferenceService = FieldReferenceService()
            }
            return fieldReferenceService
        }
    }

    fun solveFieldReference(expression: CPPASTFieldReference, statement: Statement?) {
        val fieldRefetence = FieldReference(
            expression.fieldOwner.rawSignature,
            expression.fieldName.rawSignature
        )
        StatementMapper.addStatementToStatement(statement!!, fieldRefetence)
    }
}