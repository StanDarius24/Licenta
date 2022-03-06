package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class UnaryExpression(
    var operand: Statement?,
    override val type: String? = "UnaryExpression"
) : Statement
