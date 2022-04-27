package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class SimpleTypeTemplateParameter(
    override val type: String? = "SimpleTypeTemplateParameter",
    val expression: String?
) : Statement
