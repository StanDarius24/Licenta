package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.FieldReference
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldReference

class FieldReferenceService {
    fun solveFieldReference(expression: CPPASTFieldReference, method: Method?, methodService: MethodService) {
        val fieldRefetence = FieldReference(
            expression.fieldOwner.rawSignature,
            expression.fieldName.rawSignature
        )
        methodService.addStatement(method!!, fieldRefetence)
    }
}