package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FunctionDefinition(
    override val type: String? = "FunctionDefinition",
    var declaratorSpecifier: Statement?,
    var declarator: ArrayList<Statement>?,
    var body: ArrayList<Statement>?
) : Statement {
    fun addDeclarator(statement: Statement) {
        if (declarator == null) {
            declarator = ArrayList()
        }
        declarator!!.add(statement)
    }
    fun addToBody(statement: Statement) {
        if (body == null) {
            body = ArrayList()
        }
        body!!.add(statement)
    }
}
