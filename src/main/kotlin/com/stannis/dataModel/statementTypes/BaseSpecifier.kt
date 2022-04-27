package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class BaseSpecifier(
    override val type: String? = "BaseSpecifier",
    var virtual: Boolean?,
    var name: Statement?
) : Statement
