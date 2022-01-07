package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class PackEpansionExpression(
    var pattern: Statement?,
    override val type: String? = "PackEpansionExpression"
): Statement
