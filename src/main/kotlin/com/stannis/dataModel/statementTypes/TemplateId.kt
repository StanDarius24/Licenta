package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class TemplateId(
    var templateName: Statement?,
    var templateArguments: ArrayList<Statement>?,
    override val type: String? = "TemplateId"
) : Statement {
    fun addStatement(statement: Statement) {
        if (templateArguments == null) {
            templateArguments = ArrayList()
        }
        templateArguments!!.add(statement)
    }
}
