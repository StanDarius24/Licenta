package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class SimpleDeclSpecifier(
    var declarationSpecifier: String?,
    override val type: String? = "SimpleDeclSpecifier"
) : Statement
