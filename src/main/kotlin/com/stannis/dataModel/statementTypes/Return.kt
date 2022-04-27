package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class Return(
    override val type: String? = "Return",
    var retValue: Statement?
) : Statement
