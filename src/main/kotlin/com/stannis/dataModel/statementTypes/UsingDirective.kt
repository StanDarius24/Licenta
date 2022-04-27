package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class UsingDirective(
    override val type: String? = "UsingDirective",
    var name: String?
) :
    Statement
