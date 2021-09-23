package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class While(
    var operands: ArrayList<String>?,
    var Compounds: ArrayList<Statement>?
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
}
