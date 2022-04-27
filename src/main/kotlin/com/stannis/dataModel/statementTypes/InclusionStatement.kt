package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class InclusionStatement(
    override val type: String = "InclusionStatement",
    var directive: String
) : Statement
