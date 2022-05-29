package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement

data class TemplateId(
    override val `$type`: String? = "TemplateId",
    var templateName: NameInterface?,
    var templateArguments: ArrayList<Arguments>?
) : Statement, NameInterface {
    fun addStatement(statement: Statement) {
        if (templateArguments == null) {
            templateArguments = ArrayList()
        }
        templateArguments!!.add(statement as Arguments)
    }

    override fun getWrittenName(): String {
        return templateName!!.getWrittenName()
    }
}
