package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ConstructorInitializer(
    override val `$type`: String? = "ConstructorInitializer",
    var expressions: ArrayList<Statement>?
) : Statement {

    fun addStatement(statement: Statement) {
        if (expressions == null) {
            expressions = ArrayList()
        }
        expressions!!.add(statement)
    }
}
