package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FunctionCall(
    var returntype: String?,
    var name: String?,
    var parameters: ArrayList<String>?,
    var functionCalls: ArrayList<FunctionCall>?
): Statement
{
    fun add(data: String) {
        if(parameters == null) {
            parameters = ArrayList()
        }
        parameters!!.add(data)
    }

    fun add(data: FunctionCall) {
        if(functionCalls == null) {
            functionCalls = ArrayList()
        }
        functionCalls!!.add(data)
    }
    fun addParameters(data: String) {
        if(parameters == null) {
            parameters = ArrayList()
        }
        parameters!!.add(data)
    }
}
