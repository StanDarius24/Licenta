package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ContinueStatement(
    override val type: String? = "ContinueStatement",
    var expression: String?
) : Statement
