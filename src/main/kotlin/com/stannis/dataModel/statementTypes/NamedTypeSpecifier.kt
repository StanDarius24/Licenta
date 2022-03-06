package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NamedTypeSpecifier(
    var name: String?,
    override val type: String? = "NamedTypeSpecifier"
) : Statement
