package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FunctionDeclarator(
    var parameter: ArrayList<Statement>?,
    var name: Statement?,
    override val type: String? = "FunctionDeclarator"
    ): Statement {
        fun addParameter(statement: Statement) {
            if(parameter == null) {
                parameter = ArrayList()
            }
            parameter!!.add(statement)
        }
    }
