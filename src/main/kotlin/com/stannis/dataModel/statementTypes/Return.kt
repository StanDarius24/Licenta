package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class Return(
    var retValue: Statement?,
    override val type: String? = "Return"
    ): Statement
