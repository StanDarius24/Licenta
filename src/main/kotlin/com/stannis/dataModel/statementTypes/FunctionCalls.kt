package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FunctionCalls(
    override val type: String? = "FunctionCalls",
    var name: Statement?,
    var arguments: ArrayList<Statement>?
) : Statement {
    fun addArgument(statement: Statement) {
        if (arguments == null) {
            arguments = ArrayList()
        }
        arguments!!.add(statement)
    }
}
