package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class Enumerator(
    override val type: String? = "Enumerator",
    var name: Statement?,
    var value: Statement?
) : Statement
