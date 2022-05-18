package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.DeclarationParent
import com.stannis.dataModel.DeclarationSpecifierParent
import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement

data class CompositeTypeSpecifier(
    override val `$type`: String? = "CompositeTypeSpecifier",
    var name: NameInterface?,
    var baseSpecifier: ArrayList<BaseSpecifier>?,
    var declarations: ArrayList<DeclarationParent>?,
    var key: Int?
) : Statement, DeclarationSpecifierParent {
    fun addStatement(statement: Statement) {
        if (declarations == null) {
            declarations = ArrayList()
        }
        declarations!!.add(statement as DeclarationParent)
    }

    fun addBase(statement: Statement) {
        if (baseSpecifier == null) {
            baseSpecifier = ArrayList()
        }
        baseSpecifier!!.add(statement as BaseSpecifier)
    }
}
