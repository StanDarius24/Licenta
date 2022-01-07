package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class TemplateDeclaration(
    var declaration: Statement?,
    var templateScope: Statement?,
    var parameters: ArrayList<Statement>?,
    override val type: String? = "TemplateDeclaration"

): Statement {
    fun addDeclaration(statement: Statement) {
        this.declaration = statement
    }

    fun addTemplateScope(statement: Statement) {
        this.templateScope = statement
    }

    fun addParameters(statement: Statement) {
        if(parameters == null) {
            parameters = ArrayList()
        }
        parameters!!.add(statement)
    }
}
