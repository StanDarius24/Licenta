package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NullStatement(
    override val type: String? = "NullStatement",
    var expression: String?
) :
    Statement
