package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CompoundStatement(
    var statements: ArrayList<Statement>?,
    override val type: String? = "CompoundStatement"
    ): Statement {
        fun addStatement(statement: Statement) {
            if(statements == null) {
                statements = ArrayList()
            }
            statements!!.add(statement)
        }
    }
