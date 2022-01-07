package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Statement

data class Statement(
    var name: String?,
    var value: ArrayList<String>?,
    var declaration: ArrayList<Declaration>?,
    var functionCalls: ArrayList<FunctionCall>?,
    var statement: ArrayList<Statement>?,
    override val type: String? = "Statement"
): Statement {
    fun add(data: String) {
            if (value == null) {
                value = ArrayList()
            }
        value!!.add(data)
    }
    fun add(data: FunctionCall) {
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
    fun addStatement(statement: Statement) {
        if(this.statement == null) {
            this.statement = ArrayList()
        }
        this.statement!!.add(statement)
    }
}
