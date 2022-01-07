package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ConditionalExpression(
    var condition: Statement?,
    var positiveResult: Statement?,
    var negativeResult: Statement?,
    override val type: String? = "ConditionalExpression"
) : Statement
