package com.stannis.dataModel

data class Method(
    var antet: Antet?,
    var declarations: ArrayList<Declaration>?,
    var statements: ArrayList<Statement>?,
    var methods: ArrayList<Method>?
) {
    fun addStatement(statement: Statement) {
        if(statements == null) {
            statements = ArrayList()
        }
        statements!!.add(statement)
    }
    fun addMethod(method: Method) {
        if(method == null) {
            methods = ArrayList()
        }
        methods!!.add(method)
    }
}
