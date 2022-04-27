package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CastExpression(
    override val type: String? = "CastExpression",
    var operand: Statement?,
    var typeId: Statement?
) : Statement
