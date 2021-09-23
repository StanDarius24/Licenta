package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class Return(
    var returnType: ArrayList<String>?
): Statement
{
    override fun add(data: String) {
        if(returnType == null) {
            returnType = ArrayList()
        }
        returnType!!.add(data)
    }
}
