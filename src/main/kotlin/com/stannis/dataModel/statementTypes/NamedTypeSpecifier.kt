package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NamedTypeSpecifier(
    override val `$type`: String? = "NamedTypeSpecifier",
    var name: Statement?
) : Statement
