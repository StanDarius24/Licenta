package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class For(
    var initializer: ArrayList<Statement>?,
    var conditionExpr: ArrayList<Statement>?,
    var conditionDecl: ArrayList<Statement>?,
    var iteration: ArrayList<Statement>?,
    var body: ArrayList<Statement>?,
    override val type: String? = "For"
) : Statement {

    fun addInitializer(data: Statement) {
        if (initializer == null) {
            initializer = ArrayList()
        }
        initializer!!.add(data)
    }

    fun addConditionExpression(data: Statement) {
        if (conditionExpr == null) {
            conditionExpr = ArrayList()
        }
        conditionExpr!!.add(data)
    }

    fun addConditionDeclaration(data: Statement) {
        if (conditionDecl == null) {
            conditionExpr = ArrayList()
        }
        conditionExpr!!.add(data)
    }

    fun addIteration(data: Statement) {
        if (iteration == null) {
            iteration = ArrayList()
        }
        iteration!!.add(data)
    }

    fun addBody(data: Statement) {
        if (body == null) {
            body = ArrayList()
        }
        body!!.add(data)
    }
}
