package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class TryBlockStatement(
    override val `$type`: String? = "TryBlockStatement",
    var catchHandlers: ArrayList<Statement>?,
    var tryBlock: Statement?
) : Statement {
    fun addCatchHandlers(statement: Statement) {
        if (catchHandlers == null) {
            catchHandlers = ArrayList()
        }
        catchHandlers!!.add(statement)
    }
}
