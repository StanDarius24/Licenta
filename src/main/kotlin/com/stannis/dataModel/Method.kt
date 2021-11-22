package com.stannis.dataModel

import com.stannis.dataModel.statementTypes.Initialization

data class Method(
    var antet: Antet?,
    var declarations: ArrayList<Declaration>?,
    var statements: ArrayList<Statement>?,
    var methods: ArrayList<Method>?,
    var initialz: ArrayList<Initialization>?,
    var modifier: String?
) {
    fun addInitialz(initialization: Initialization) {
        if(initialz == null) {
            initialz = ArrayList()
        }
        initialz!!.add(initialization)
    }

    fun addStatement(statement: Statement) {
        if(statements == null) {
            statements = ArrayList()
        }
        statements!!.add(statement)
    }
    fun addMethod(method: Method) {
        if(methods == null) {
            methods = ArrayList()
        }
        methods!!.add(method)
    }
}
