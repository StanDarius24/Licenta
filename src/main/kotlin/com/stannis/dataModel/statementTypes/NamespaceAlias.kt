package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NamespaceAlias(
    override val type: String? = "NamespaceAlias",
    var alias: Statement?,
    var qualifiedName: Statement?
) : Statement
