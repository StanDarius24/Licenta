package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CompositeTypeSpecifier(
    var name: String?,
    var declarations: ArrayList<Statement>?
): Statement {
    fun addStatement(statement: Statement) {
        if(declarations == null) {
            declarations = ArrayList()
        }
        declarations!!.add(statement)
    }
}
