package com.stannis.dataModel

data class Class(
    var name: String?,
    var inheritance: ArrayList<String>?,
    var declarations: ArrayList<Declaration?>?,
    var methods: ArrayList<Method?>?
    ): Statement {

    fun addDeclaration(decl: Declaration) {
        if(declarations == null) {
            declarations = ArrayList()
        }
        declarations!!.add(decl)
    }

    fun addMethod(meth: Method) {
        if(methods == null) {
            methods = ArrayList()
        }
        methods!!.add(meth)
    }

    fun addInheritance(superClass: String) {
        if(inheritance == null) {
            inheritance = ArrayList()
        }
        inheritance!!.add(superClass)
    }
}
