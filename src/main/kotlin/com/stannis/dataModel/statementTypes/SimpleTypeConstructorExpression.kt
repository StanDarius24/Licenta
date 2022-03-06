package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class SimpleTypeConstructorExpression(
    var castType: String?,
    var parameters: ArrayList<Statement>?,
    override val type: String? = "SimpleTypeConstructorExpression"
) : Statement {
    fun addParameter(statement: Statement) {
        if (parameters == null) {
            parameters = ArrayList()
        }
        parameters!!.add(statement)
    }
}
