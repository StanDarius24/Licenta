package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement

data class ConditionalExpression(
    override val `$type`: String? = "ConditionalExpression",
    var condition: Arguments?,
    var positiveResult: Arguments?,
    var negativeResult: Arguments?
) : Statement, Arguments
