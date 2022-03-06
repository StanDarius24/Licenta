package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class While(
    var condition: Statement?,
    var condition2: Statement?,
    var body: Statement?,
    override val type: String? = "While"
) : Statement
