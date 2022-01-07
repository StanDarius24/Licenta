package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FieldReference(
    var parent: String?,
    var field: String?,
    override val type: String? = "FieldReference"
): Statement
