package com.stannis.dataModel

import com.stannis.dataModel.statementTypes.Statement

data class Method(
    var antet: Antet?,
    var declarations: ArrayList<Declaration>?,
    var statements: ArrayList<com.stannis.dataModel.Statement>?,
    var methods: ArrayList<com.stannis.dataModel.Statement>?,
    var initialz: ArrayList<Statement>?,
    var modifier: String?,
    override val type: String? = "Method"

): com.stannis.dataModel.Statement {
    fun addInitialz(initialization: Statement) {
        if(initialz == null) {
            initialz = ArrayList()
        }
        initialz!!.add(initialization)
    }

    fun addStatement(statement: com.stannis.dataModel.Statement) {
        if(statements == null) {
            statements = ArrayList()
        }
        statements!!.add(statement)
    }
    fun addMethod(method: com.stannis.dataModel.Statement) {
        if(methods == null) {
            methods = ArrayList()
        }
        methods!!.add(method)
    }
}
