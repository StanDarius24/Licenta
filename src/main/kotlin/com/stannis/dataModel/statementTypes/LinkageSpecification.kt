package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class LinkageSpecification(
    override val type: String? = "LinkageSpecification",
    var literal: String?,
    val declarations: ArrayList<Statement>?
) : Statement
