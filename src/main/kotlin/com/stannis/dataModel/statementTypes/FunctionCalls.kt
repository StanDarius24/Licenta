package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FunctionCalls(
    var name: Statement?,
    var arguments: ArrayList<Statement>?,
    override val type: String? = "FunctionCalls"
) : Statement {
    fun addArgument(statement: Statement) {
        if (arguments == null) {
            arguments = ArrayList()
        }
        arguments!!.add(statement)
    }
}
