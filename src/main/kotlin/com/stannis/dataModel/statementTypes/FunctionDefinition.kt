package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FunctionDefinition(
    var declaratorSpecifier: Statement?,
    var declarator: ArrayList<Statement>?,
    var body: Statement?
): Statement {
    fun addDeclarator(statement: Statement) {
        if(declarator == null) {
            declarator = ArrayList()
        }
        declarator!!.add(statement)
    }
}
