package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement

data class IdExpression(
    override val `$type`: String? = "IdExpression",
    var expression: NameInterface?
) : Statement, Arguments
