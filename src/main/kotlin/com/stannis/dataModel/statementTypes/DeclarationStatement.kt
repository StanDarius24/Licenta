package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class DeclarationStatement(
    override val type: String? = "DeclarationStatement",
    var declarations: ArrayList<Statement>?
) : Statement {
    fun addDeclaration(statement: Statement) {
        if (declarations == null) {
            declarations = ArrayList()
        }
        declarations!!.add(statement)
    }
}
