package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

//data.initializerStatement // declaration Statement int x = 0;
//data.conditionExpression // conditia i<5 || calc();
//data.conditionDeclaration // declaration in condition
//data.iterationExpression // iteratii Binary expr / IdExpr etc..
//data.body // compound statement

data class For(
    var initializer: ArrayList<String>?,
    var conditionExpr: ArrayList<String>?,
    var conditionDecl: ArrayList<String>?,
    var iteration: ArrayList<String>?,
    var statement: ArrayList<Statement>?
): Statement {

    fun addInitializer(data: String) {
        if(initializer == null) {
            initializer = ArrayList()
        }
        initializer!!.add(data)
    }

    fun addConditionExpr(data: String) {
        if(conditionExpr == null) {
            conditionExpr = ArrayList()
        }
        conditionExpr!!.add(data)
    }

    fun addConditionDecl(data: String) {
        if(conditionDecl == null) {
            conditionDecl = ArrayList()
        }
        conditionDecl!!.add(data)
    }

    fun addIteration(data: String) {
        if(iteration == null) {
            iteration = ArrayList()
        }
        iteration!!.add(data)
    }

    fun addStatement(statement: Statement) {
        this.statement!!.add(statement) //????????
    }

    override fun add(data: String) {
        TODO("Not yet implemented")
    }
}
