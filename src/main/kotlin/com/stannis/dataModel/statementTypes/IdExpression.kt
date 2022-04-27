package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class IdExpression(
    var expression: Statement?,
    override val type: String? = "IdExpression") :
    Statement
