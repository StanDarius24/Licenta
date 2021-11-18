package com.stannis.dataModel

data class Class(
    var name: String?,
    var declarations: ArrayList<Declaration?>?,
    var methods: ArrayList<Method?>?
    ) {
    fun addName(name: String) {
        this.name = name
    }

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
}
