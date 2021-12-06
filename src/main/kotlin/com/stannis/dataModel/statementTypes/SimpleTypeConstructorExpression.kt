package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class SimpleTypeConstructorExpression(
    var castType: String?,
    var parameters: ArrayList<Statement>?
): Statement {
    fun addCastType(name: String) {
        castType = name
    }

    fun addParameter(statement: Statement) {
        if(parameters == null) {
            parameters = ArrayList()
        }
        parameters!!.add(statement)
    }
}
