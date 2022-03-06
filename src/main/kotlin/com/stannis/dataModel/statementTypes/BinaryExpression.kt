package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class BinaryExpression(
    var leftExpression: Statement?,
    var rightExpression: Statement?,
    override val type: String? = "BinaryExpression"
) : Statement {
    fun addLeftExpression(statement: Statement) {
        this.leftExpression = statement
    }

    fun addRightExpression(statement: Statement) {
        this.rightExpression = statement
    }
}
