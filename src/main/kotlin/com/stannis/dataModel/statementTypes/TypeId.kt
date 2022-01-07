package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class TypeId(
    var declSpecifier: Statement?,
    var abstractDeclaration: Statement?,
    override val type: String? = "TypeId"

): Statement
