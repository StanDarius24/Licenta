package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FieldReference(
    override val type: String? = "FieldReference",
    var fieldName: Statement?,
    var fieldOwner: Statement?
) : Statement
