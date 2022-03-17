package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.ComplexCompositeTypeSpecifier
import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier

object CompositeTypeRegistry {

    var list: ArrayList<ComplexCompositeTypeSpecifier>? = null
    lateinit var filepath: String

    fun addCompositeTypeSpecifier(node: CompositeTypeSpecifier) {
        if (list == null) {
            list = ArrayList()
        }
        list!!.add(ComplexCompositeTypeSpecifier(node, this.filepath))
    }

    fun setPath(filepath: String) {
        this.filepath = filepath
    }
}
