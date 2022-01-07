package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class LabelStatement(
    var name: String?,
    var expressions: ArrayList<Statement>?,
    override val type: String? = "LabelStatement"
): Statement {
    fun addExpression(statement: Statement) {
        if(expressions == null) {
            expressions = ArrayList()
        }
        expressions!!.add(statement)
    }
}
