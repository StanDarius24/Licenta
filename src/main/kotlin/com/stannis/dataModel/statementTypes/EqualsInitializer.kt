package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class EqualsInitializer(
    override val `$type`: String? = "EqualsInitializer",
    var functionName: Statement?,
    var statements: ArrayList<Statement>?
) : Statement {
    fun addStatement(statement: Statement) {
        if (statements == null) {
            statements = ArrayList()
        }
        statements!!.add(statement)
    }
}
