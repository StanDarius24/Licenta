package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class DeleteExpression(
    override val type: String? = "DeleteExpression",
    var deletedExpression: String?
) : Statement
