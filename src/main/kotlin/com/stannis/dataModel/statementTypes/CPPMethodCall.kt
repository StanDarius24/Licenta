package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CPPMethodCall(
    var name:String?,
    var functionCall: ArrayList<FunctionCall>?,
    override val type: String? = "CPPMethodCall"
): Statement {
    fun add(data: String) {
        name = data
    }

    fun add(data: FunctionCall) {
        if(functionCall == null) {
            functionCall = ArrayList()
        }
        functionCall!!.add(data)
    }
}
