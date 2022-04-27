package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CaseStatement(
    override val `$type`: String? = "CaseStatement",
    var expression: String?
) :
    Statement
