package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class TemplateSpecialization(
    override val type: String? = "TemplateSpecialization",
    var declaration: Statement?
) : Statement
