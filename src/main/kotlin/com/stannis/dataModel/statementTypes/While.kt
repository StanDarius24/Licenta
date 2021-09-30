package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement

data class While(
    var operands: ArrayList<String>?,
    var Compounds: ArrayList<Statement>?,
    var functionCalls: ArrayList<FunctionCall>?,
    var methods: ArrayList<Method>?
): Statement {

    override fun add(data: String) {
        if(operands == null) {
            operands = ArrayList()
        }
        operands!!.add(data)
    }

    fun addStatement(statement: Statement) {
        if (Compounds == null) {
            Compounds = ArrayList()
        }
        Compounds!!.add(statement)
    }

    fun addblock(block: Method) {
        if (methods == null) {
            methods = ArrayList()
        }
        methods!!.add(block)
    }

    override fun add(data: FunctionCall) {
        if(functionCalls == null) {
            functionCalls = ArrayList()
        }
        functionCalls!!.add(data)
    }
}
