package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class AnonimStatement(
    var statement: Statement?,
    override val type: String = "AnonimStatement"
): Statement
