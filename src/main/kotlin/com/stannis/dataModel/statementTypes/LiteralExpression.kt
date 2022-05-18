package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement

data class LiteralExpression(
    override val `$type`: String? = "LiteralExpression",
    var expression: String?
) : Statement, Arguments
