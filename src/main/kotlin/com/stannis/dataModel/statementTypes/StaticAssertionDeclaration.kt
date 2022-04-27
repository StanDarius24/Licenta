package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class StaticAssertionDeclaration(
    override val type: String? = "StaticAssertionDeclaration",
    var condition: Statement?,
    var message: Statement?
) : Statement {

    fun addCondition(statement: Statement) {
        this.condition = statement
    }

    fun addMessage(statement: Statement) {
        this.message = statement
    }
}
