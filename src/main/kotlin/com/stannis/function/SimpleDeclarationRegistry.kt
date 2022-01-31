package com.stannis.function

import com.stannis.dataModel.statementTypes.SimpleDeclaration

object SimpleDeclarationRegistry {
    var list: ArrayList<SimpleDeclaration>? = null

    fun addToList(data: SimpleDeclaration) {
        if(list == null) {
            list = ArrayList()
        }
        list!!.add(data)
        println(data)
    }

    fun clearList() {
        list = null
    }
}