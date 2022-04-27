package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NewExpression(
    override val type: String? = "NewExpression",
    var typeId: Statement?,
    var initializer: Statement?
) : Statement
