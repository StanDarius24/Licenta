package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Statement

data class Initialization(
    var name: String?,
    var value: ArrayList<String>?,
    var declaration: ArrayList<Declaration>?,
    var functionCalls: ArrayList<FunctionCall>?
): Statement {
    override fun add(data: String) {
            if (value == null) {
                value = ArrayList()
            }
        value!!.add(data)
    }
    override fun add(data: FunctionCall) {
        if (functionCalls == null) {
            functionCalls = ArrayList()
        }
        functionCalls!!.add(data)
    }
    fun add(data: Declaration) {
        if(declaration == null) {
            declaration = ArrayList()
        }
        declaration!!.add(data)
    }
}
