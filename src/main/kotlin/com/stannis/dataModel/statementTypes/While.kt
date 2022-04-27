package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class While(
    override val `$type`: String? = "While",
    var condition: Statement?,
    var condition2: Statement?,
    var body: Statement?,
) : Statement
