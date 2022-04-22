package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ProblemExpression(
    var problem: String?,
    override val type: String = "Problem Expression"
    ): Statement
