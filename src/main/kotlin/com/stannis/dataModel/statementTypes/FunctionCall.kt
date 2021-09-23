package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FunctionCall(
    var returntype: String?,
    var name: String?,
    var parameters: ArrayList<String>?,
    var functcalls: ArrayList<FunctionCall>?
): Statement
{
    override fun add(data: String) {
        if(parameters == null) {
            parameters = ArrayList()
        }
        parameters!!.add(data)
    }

    fun add(data: FunctionCall) {
        if(functcalls == null) {
            functcalls = ArrayList()
        }
        functcalls!!.add(data)
    }
}
