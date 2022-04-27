package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class UsingDeclaration(
    override val `$type`: String? = "UsingDeclaration",
    var name: String?
) :
    Statement
