package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class AliasDeclaration(
    override val type: String? = "AliasDeclaration",
    var aliasName: String?,
    var mappingTypeId: String?
) : Statement
