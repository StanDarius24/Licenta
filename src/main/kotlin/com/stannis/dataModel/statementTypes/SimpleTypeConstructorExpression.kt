package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement

data class SimpleTypeConstructorExpression(
    override val `$type`: String? = "SimpleTypeConstructorExpression",
    var castType: String?,
    var parameters: ArrayList<Arguments>?
) : Statement, Arguments {
    fun addParameter(statement: Statement) {
        if (parameters == null) {
            parameters = ArrayList()
        }
        parameters!!.add(statement as Arguments)
    }
}
