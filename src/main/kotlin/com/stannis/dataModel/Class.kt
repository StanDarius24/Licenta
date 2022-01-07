package com.stannis.dataModel

data class Class(
    var name: String?,
    var inheritance: ArrayList<String>?,
    var declarations: ArrayList<Statement?>?,
    override val type: String? = "Class"
): Statement {

    fun addStatement(decl: Statement) {
        if(declarations == null) {
            declarations = ArrayList()
        }
        declarations!!.add(decl)
    }

    fun addInheritance(superClass: String) {
        if(inheritance == null) {
            inheritance = ArrayList()
        }
        inheritance!!.add(superClass)
    }
}
