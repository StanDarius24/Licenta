package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class EqualsInitializer(
    var expression: String?,
    var statement: Statement?,
    override val type: String? = "EqualsInitializer"
): Statement {
    fun addStatement(statement: Statement) {
        this.statement = statement
    }
}
