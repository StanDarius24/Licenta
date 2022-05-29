package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement

data class QualifiedName(
    override val `$type`: String? = "QualifiedName",
    var qualifier: ArrayList<NameInterface>?,
    var lastName: NameInterface?
) : Statement, NameInterface {
    fun addQualifier(statement: Statement) {
        if (qualifier == null) {
            qualifier = ArrayList()
        }
        qualifier!!.add(statement as NameInterface)
    }

    override fun getWrittenName(): String {
        return lastName!!.getWrittenName()
    }
}
