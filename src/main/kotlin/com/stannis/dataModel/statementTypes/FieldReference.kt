package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FieldReference(
    var fieldName: Statement?,
    var fieldOwner: Statement?,
    override val type: String? = "FieldReference"
): Statement
