package com.stannis.function

import com.stannis.dataModel.statementTypes.LinkageSpecification

object ExternDefinitionRegistry {

    var listOfExtern: ArrayList<LinkageSpecification>? = null

    fun addToList(linkageSpecification: LinkageSpecification) {
        if (listOfExtern == null) {
            listOfExtern = ArrayList()
        }
        listOfExtern!!.add(linkageSpecification)
    }
}
