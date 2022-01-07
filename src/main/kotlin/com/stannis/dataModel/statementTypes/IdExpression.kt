package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class IdExpression(
    val expression: String?,
    override val type: String? = "IdExpression"
): Statement
