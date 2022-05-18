package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement

data class InitializerList(
    override val `$type`: String? = "InitializerList",
    var initializers: ArrayList<Arguments>?
) : Statement, Arguments {
    fun addInitializers(statement: Statement) {
        if (initializers == null) {
            initializers = ArrayList()
        }
        initializers!!.add(statement as Arguments)
    }
}
