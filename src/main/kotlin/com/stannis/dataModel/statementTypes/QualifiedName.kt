package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class QualifiedName(
    var qualifier: ArrayList<Statement>?,
    var lastName: Statement?
    ): Statement {
    fun addQualifier(statement: Statement) {
        if(qualifier == null) {
            qualifier = ArrayList()
        }
        qualifier!!.add(statement)
    }
}