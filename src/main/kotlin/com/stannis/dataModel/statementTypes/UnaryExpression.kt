package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class UnaryExpression(
    override val type: String? = "UnaryExpression",
    var operand: Statement?
) : Statement
