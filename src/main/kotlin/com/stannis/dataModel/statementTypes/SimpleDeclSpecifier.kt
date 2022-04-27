package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class SimpleDeclSpecifier(
    override val `$type`: String? = "SimpleDeclSpecifier",
    var declarationSpecifier: String?
) : Statement
