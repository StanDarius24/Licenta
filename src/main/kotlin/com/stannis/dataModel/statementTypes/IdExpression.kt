package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class IdExpression(
    override val `$type`: String? = "IdExpression",
    var expression: Statement?
) :
    Statement
