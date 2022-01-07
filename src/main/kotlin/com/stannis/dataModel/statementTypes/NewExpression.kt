package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NewExpression(
    var newExpression: FunctionCall?,
    override val type: String? = "NewExpression"
): Statement {
    fun addNewExpression(functionCall: FunctionCall) {
        newExpression = functionCall
    }
}
