package com.stannis.function

import com.stannis.dataModel.statementTypes.NameSpace

object NameSpaceRegistry {

    var listOfNameSpace: ArrayList<NameSpace>? = null

    fun addNameSpace(registry: NameSpace){
        if(listOfNameSpace == null) {
            listOfNameSpace = ArrayList()
        }
        listOfNameSpace!!.add(registry)
    }
}