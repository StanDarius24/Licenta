package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class TemplateId(
    override val `$type`: String? = "TemplateId",
    var templateName: Statement?,
    var templateArguments: ArrayList<Statement>?
) : Statement {
    fun addStatement(statement: Statement) {
        if (templateArguments == null) {
            templateArguments = ArrayList()
        }
        templateArguments!!.add(statement)
    }
}
