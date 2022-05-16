package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NameSpace(
    override val `$type`: String? = "NameSpace",
    var isInline: Boolean?,
    var name: String?,
    var declarations: ArrayList<Statement>?
) : Statement {
    fun addDeclaration(statement: Statement) {
        if(declarations == null) {
            declarations = ArrayList()
        }
        declarations!!.add(statement)
    }
}
