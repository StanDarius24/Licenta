package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NewExpression(
    var newExpression: FunctionCall?
): Statement {
    fun addNewExpression(functionCall: FunctionCall) {
        newExpression = functionCall
    }
}
