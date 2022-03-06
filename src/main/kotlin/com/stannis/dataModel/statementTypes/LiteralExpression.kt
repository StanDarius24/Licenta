package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class LiteralExpression(
    var expression: String?,
    override val type: String? = "LiteralExpression"
) : Statement
