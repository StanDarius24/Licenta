package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ProblemStatement(
    override val `$type`: String? = "ProblemStatement",
    var expression: String?
) : Statement
