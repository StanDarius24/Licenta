package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FunctionCall(
    override val `$type`: String? = "FunctionCall",
    var returntype: String?,
    var name: String?,
    var parameters: ArrayList<String>?,
    var complexParameters: ArrayList<Statement>?,
    var functionCalls: ArrayList<FunctionCall>?
) : Statement {
    fun addComplexParameters(statement: Statement) {
        if (complexParameters == null) {
            complexParameters = ArrayList()
        }
        complexParameters!!.add(statement)
    }

    fun add(data: String) {
        if (parameters == null) {
            parameters = ArrayList()
        }
        parameters!!.add(data)
    }

    fun add(data: FunctionCall) {
        if (functionCalls == null) {
            functionCalls = ArrayList()
        }
        functionCalls!!.add(data)
    }
}
