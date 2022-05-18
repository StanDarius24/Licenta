package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement

data class BinaryExpression(
    override val `$type`: String? = "BinaryExpression",
    var leftExpression: Arguments?,
    var rightExpression: Arguments?
) : Statement, Arguments {
    fun addLeftExpression(statement: Statement) {
        this.leftExpression = statement as Arguments
    }

    fun addRightExpression(statement: Statement) {
        this.rightExpression = statement as Arguments
    }
}
