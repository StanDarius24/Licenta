package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.HigherClass
import com.stannis.dataModel.Statement

data class FunctionCalls(
    override val `$type`: String? = "FunctionCalls",
    var name: HigherClass?,
    var arguments: ArrayList<Arguments>?
) : Statement, Arguments {
    fun addArgument(statement: Statement) {
        if (arguments == null) {
            arguments = ArrayList()
        }
        arguments!!.add(statement as Arguments)
    }
}
