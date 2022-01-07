package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class Declarator(
    var name: String?,
    var initialization: Statement?,
    override val type: String? = "Declarator"
): Statement
