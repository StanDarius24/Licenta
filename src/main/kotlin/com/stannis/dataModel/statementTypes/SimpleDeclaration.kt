package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class SimpleDeclaration(
    var declarators: ArrayList<Statement>?,
    var declSpecifier: Statement?,
    override val type: String? = "SimpleDeclaration"
):Statement {
    fun addDeclarators(statement: Statement) {
        if(declarators == null) {
            declarators = ArrayList()
        }
        declarators!!.add(statement)
    }
}
