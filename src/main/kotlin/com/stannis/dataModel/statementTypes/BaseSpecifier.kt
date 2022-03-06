package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class BaseSpecifier(
    var virtual: Boolean?,
    var name: Statement?,
    override val type: String? = "BaseSpecifier"
) : Statement
