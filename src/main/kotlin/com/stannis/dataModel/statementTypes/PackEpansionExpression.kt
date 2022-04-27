package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class PackEpansionExpression(
    override val type: String? = "PackEpansionExpression",
    var pattern: Statement?
) : Statement
