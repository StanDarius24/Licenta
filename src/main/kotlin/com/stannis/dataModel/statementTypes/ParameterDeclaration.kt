package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ParameterDeclaration(
    override val `$type`: String? = "ParameterDeclaration",
    var declarationSpecifier: Statement?,
    var declarator: Statement?
) : Statement
