package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class InclusionStatement(
    var directive: String,
    override val type: String = "InclusionStatement"
) : Statement
