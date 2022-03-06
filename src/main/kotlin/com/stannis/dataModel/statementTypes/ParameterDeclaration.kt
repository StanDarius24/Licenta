package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ParameterDeclaration(
    var declarationSpecifier: Statement?,
    var declarator: Statement?,
    override val type: String? = "ParameterDeclaration"
) : Statement
