package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class TypeId(
    override val type: String? = "TypeId",
    var declSpecifier: Statement?,
    var abstractDeclaration: Statement?
) : Statement
