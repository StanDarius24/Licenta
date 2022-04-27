package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ConditionalExpression(
    override val `$type`: String? = "ConditionalExpression",
    var condition: Statement?,
    var positiveResult: Statement?,
    var negativeResult: Statement?
) : Statement
