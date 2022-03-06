package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ProblemStatement(
    var expression: String?,
    override val type: String? = "ProblemStatement"
) : Statement
