package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.DeclarationParent
import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement

data class Name(
    override val `$type`: String? = "Name",
    var name: String?
) : Statement, DeclarationParent, NameInterface {
    override fun getWrittenName(): String {
        return name!!
    }

}
