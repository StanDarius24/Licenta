package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class If(
    override val `$type`: String? = "If",
    var condition: Statement?,
    var thenClause: Statement?,
    var elseClause: Statement?,
    var condDecl: Statement?
) : Statement
