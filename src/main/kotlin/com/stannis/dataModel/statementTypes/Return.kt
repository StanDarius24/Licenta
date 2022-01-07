package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class Return(
    var returnType: ArrayList<String>?,
    var functionCalls: ArrayList<FunctionCall>?,
    var statement: Statement?,
    override val type: String? = "Return"
): Statement
{
    fun addStatement(statement: Statement?) {
        this.statement = statement
    }
    fun add(data: String) {
        if(returnType == null) {
            returnType = ArrayList()
        }
        returnType!!.add(data)
    }
    fun add(data: FunctionCall) {
        if (functionCalls == null) {
            functionCalls = ArrayList()
        }
        functionCalls!!.add(data)
    }
}
