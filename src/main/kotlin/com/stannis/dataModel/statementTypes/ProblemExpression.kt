package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ProblemExpression(
    override val `$type`: String = "Problem Expression",
    var problem: String?
    ): Statement
