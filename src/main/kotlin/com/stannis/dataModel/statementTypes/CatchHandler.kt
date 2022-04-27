package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CatchHandler(
    override val `$type`: String? = "CatchHandler",
    var body: Statement?
) :
    Statement
