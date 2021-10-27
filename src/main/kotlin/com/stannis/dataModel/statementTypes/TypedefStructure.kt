package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Statement

data class TypedefStructure(
    var name: String?,
    var initialization: Declaration?,
    var parameters: ArrayList<Declaration>?
): Statement {

    fun addInit(init: Declaration) {
        initialization = init
    }

    override fun add(data: String) {
        name = data
    }

    fun add(data: Declaration) {
        if(parameters == null) {
            parameters = ArrayList()
        }
        parameters!!.add(data)
    }

    override fun add(data: FunctionCall) {
        TODO("Not yet implemented")
    }
}
