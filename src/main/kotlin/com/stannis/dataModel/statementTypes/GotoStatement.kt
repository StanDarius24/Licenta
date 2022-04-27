package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class GotoStatement(
    override val type: String? = "GotoStatement",
    var jumpTo: String?
) :
    Statement
