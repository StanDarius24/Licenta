package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class EqualsInitializer(
    var functionName: Statement?,
    var statements: ArrayList<Statement>?,
    override val type: String? = "EqualsInitializer"
) : Statement {
    fun addStatement(statement: Statement) {
        if (statements == null) {
            statements = ArrayList()
        }
        statements!!.add(statement)
    }
}
