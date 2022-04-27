package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class SimpleDeclaration(
    override val `$type`: String? = "SimpleDeclaration",
    var declarators: ArrayList<Statement>?,
    var declSpecifier: Statement?
) : Statement {
    fun addDeclarators(statement: Statement) {
        if (declarators == null) {
            declarators = ArrayList()
        }
        declarators!!.add(statement)
    }
}
