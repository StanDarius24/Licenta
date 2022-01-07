package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CatchHandler(
    var body: Statement?,
    override val type: String? = "CatchHandler"
): Statement
