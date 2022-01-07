package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class StaticAssertionDeclaration(
    var condition: Statement?,
    var message: Statement?,
    override val type: String? = "StaticAssertionDeclaration"

): Statement {

    fun addCondition(statement: Statement) {
        this.condition = statement
    }

    fun addMessage(statement: Statement) {
        this.message = statement
    }
}
