package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NamedTypeSpecifier(
    var name: Statement?,
    override val type: String? = "NamedTypeSpecifier"
) : Statement
