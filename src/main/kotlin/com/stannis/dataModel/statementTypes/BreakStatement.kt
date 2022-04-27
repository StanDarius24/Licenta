package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class BreakStatement(
    override val `$type`: String? = "BreakStatement",
    var expression: String?
) :
    Statement
