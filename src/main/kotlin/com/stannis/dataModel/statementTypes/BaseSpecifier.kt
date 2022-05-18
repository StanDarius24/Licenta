package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class BaseSpecifier(
    override val `$type`: String? = "BaseSpecifier",
    var virtua: Boolean?,
    var name: Statement?
) : Statement
