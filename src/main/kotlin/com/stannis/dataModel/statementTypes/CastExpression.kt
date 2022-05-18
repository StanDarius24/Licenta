package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement

data class CastExpression(
    override val `$type`: String? = "CastExpression",
    var operand: Arguments?,
    var typeId: Arguments?
) : Statement, Arguments
