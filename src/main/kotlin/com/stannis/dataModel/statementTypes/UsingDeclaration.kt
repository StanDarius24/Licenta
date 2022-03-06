package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class UsingDeclaration(var name: String?, override val type: String? = "UsingDeclaration") :
    Statement
