package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class Name(
    override val `$type`: String? = "Name",
    var name: String?
) : Statement
