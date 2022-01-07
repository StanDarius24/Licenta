package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class RangeBasedForStatement(
    var declaration: ArrayList<Statement>?,
    var initClause: ArrayList<Statement>?,
    var body: ArrayList<Statement>?,
    override val type: String? = "RangeBaseForStatement"
): Statement {

    fun addDeclaration(statement: Statement) {
        if(declaration == null) {
            declaration = ArrayList()
        }
        declaration!!.add(statement)
    }

    fun addInitClause(statement: Statement) {
        if(initClause == null) {
            initClause = ArrayList()
        }
        initClause!!.add(statement)
    }

    fun addBody(statement: Statement) {
        if(body == null) {
            body = ArrayList()
        }
        body!!.add(statement)
    }
}
