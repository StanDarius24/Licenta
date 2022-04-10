package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class LinkageSpecification(
    var literal: String?,
    val declarations: ArrayList<Statement>?,
    override val type: String? = "LinkageSpecification"
) : Statement
