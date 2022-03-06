package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class SimpleTypeTemplateParameter(
    val expression: String?,
    override val type: String? = "SimpleTypeTemplateParameter"
) : Statement
