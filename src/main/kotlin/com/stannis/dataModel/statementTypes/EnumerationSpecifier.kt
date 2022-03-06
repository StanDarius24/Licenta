package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class EnumerationSpecifier(
    var name: String?,
    var enumerators: ArrayList<Statement>?,
    override val type: String? = "EnumerationSpecifier"
) : Statement {
    fun addEnumerators(statement: Statement) {
        if (enumerators == null) {
            enumerators = ArrayList()
        }
        enumerators!!.add(statement)
    }
}
