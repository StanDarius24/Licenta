package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class UsingDirective(var name: String?, override val type: String? = "UsingDirective") :
    Statement
