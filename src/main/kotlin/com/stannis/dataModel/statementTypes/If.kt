package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement

data class If(var operands: ArrayList<String>?,
              var Compounds: ArrayList<Statement>?,
              var functionCalls: ArrayList<FunctionCall>?,
              var ifBlock: ArrayList<Method>?,
              var elseBlock: ArrayList<Method>?): Statement {

    fun add(data: String) {
        if(operands == null) {
            operands = ArrayList()
        }
        operands!!.add(data)
    }

    fun add(data: FunctionCall) {
        if(functionCalls == null) {
            functionCalls = ArrayList()
        }
        functionCalls!!.add(data)
    }

    fun addStatement(statement: Statement) {
        if (Compounds == null) {
            Compounds = ArrayList()
        }
        Compounds!!.add(statement)
    }

    fun addIfBlock(block: Method) {
        if (ifBlock == null) {
            ifBlock = ArrayList()
        }
        ifBlock!!.add(block)
    }

    fun addElseBlock(block: Method) {
        if(elseBlock == null) {
            elseBlock = ArrayList()
        }
        elseBlock!!.add(block)
    }
}
