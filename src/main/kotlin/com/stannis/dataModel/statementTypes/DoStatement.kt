package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class DoStatement(
    var condition: ArrayList<Statement>?,
    var body: ArrayList<Statement>?,
    override val type: String? = "DoStatement"
): Statement {

    fun addToCondition(statement: Statement) {
        if(condition == null) {
            condition = ArrayList()
        }
        condition!!.add(statement)
    }

    fun addToBody(statement: Statement) {
        if(body == null) {
            body = ArrayList()
        }
        body!!.add(statement)
    }

}
