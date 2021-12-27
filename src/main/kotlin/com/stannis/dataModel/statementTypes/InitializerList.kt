package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class InitializerList(
    var initializers: ArrayList<Statement>?
): Statement {
    fun addInitializers(statement: Statement) {
        if(initializers == null) {
            initializers = ArrayList()
        }
        initializers!!.add(statement)
    }
}
