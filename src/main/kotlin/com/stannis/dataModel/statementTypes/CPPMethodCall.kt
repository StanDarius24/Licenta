package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CPPMethodCall(
    var name:String?,
    var functionCall: ArrayList<FunctionCall>?
): Statement {
    override fun add(data: String) {
        name = data
    }

    override fun add(data: FunctionCall) {
        if(functionCall == null) {
            functionCall = ArrayList()
        }
        functionCall!!.add(data)
    }
}
