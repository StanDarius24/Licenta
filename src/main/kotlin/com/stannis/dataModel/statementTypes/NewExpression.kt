package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NewExpression(
    var typeId: Statement?,
    var initializer: Statement?,
    override val type: String? = "NewExpression"
) : Statement
