package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.DeclarationParent
import com.stannis.dataModel.Statement

data class TemplateDeclaration(
    override val `$type`: String? = "TemplateDeclaration",
    var declaration: Statement?,
    var parameters: ArrayList<Statement>?
) : Statement, DeclarationParent {
    fun addDeclaration(statement: Statement) {
        this.declaration = statement
    }

    fun addParameters(statement: Statement) {
        if (parameters == null) {
            parameters = ArrayList()
        }
        parameters!!.add(statement)
    }
}
