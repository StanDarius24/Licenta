package com.stannis.function

import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier

object CompositeTypeRegistry {

    var list: ArrayList<CompositeTypeSpecifier>? = null

    fun addCompositeTypeSpecifier(node: CompositeTypeSpecifier) {
        if(list == null) {
            list = ArrayList()
        }
        list!!.add(node)
    }

}
