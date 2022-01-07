package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class TemplateSpecialization(
    var declaration: Statement?,
    override val type: String? = "TemplateSpecialization"

): Statement
