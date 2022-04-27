package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class AnonimStatement(
    override val type: String = "AnonimStatement",
    var statement: Statement?
) : Statement {

    companion object {
        fun getNewAnonimStatement(): AnonimStatement {
            return AnonimStatement(statement = null)
        }
    }
}
