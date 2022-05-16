package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.DeclarationSpecifierParent
import com.stannis.dataModel.Statement

data class EnumerationSpecifier(
    override val `$type`: String? = "EnumerationSpecifier",
    var name: String?,
    var enumerators: ArrayList<Statement>?
) : Statement, DeclarationSpecifierParent {
    fun addEnumerators(statement: Statement) {
        if (enumerators == null) {
            enumerators = ArrayList()
        }
        enumerators!!.add(statement)
    }
}
