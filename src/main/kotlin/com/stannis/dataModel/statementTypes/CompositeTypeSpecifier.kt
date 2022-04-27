package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CompositeTypeSpecifier(
    override val type: String? = "CompositeTypeSpecifier",
    var name: Statement?,
    var baseSpec: ArrayList<Statement>?,
    var declarations: ArrayList<Statement>?,
    var key: Int?
) : Statement {
    fun addStatement(statement: Statement) {
        if (declarations == null) {
            declarations = ArrayList()
        }
        declarations!!.add(statement)
    }

    fun addBase(statement: Statement) {
        if (baseSpec == null) {
            baseSpec = ArrayList()
        }
        baseSpec!!.add(statement)
    }
}
