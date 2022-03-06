package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class DeclarationStatement(
    var declarations: ArrayList<Statement>?,
    override val type: String? = "DeclarationStatement"
) : Statement {
    fun addDeclaration(statement: Statement) {
        if (declarations == null) {
            declarations = ArrayList()
        }
        declarations!!.add(statement)
    }
}
