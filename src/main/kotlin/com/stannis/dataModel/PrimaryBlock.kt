package com.stannis.dataModel

data class PrimaryBlock(
    var statements: ArrayList<Statement>?,
    override val `$type`: String? = "PrimaryBlock"
) : Statement {
    fun addStatement(statement: Statement) {
        if (statements == null) {
            statements = ArrayList()
        }
        statements!!.add(statement)
    }
}
