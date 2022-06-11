package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement

data class DeclWithParent(
    override val `$type`: String? = "DeclWithParent",
    var declaration: Statement?,
    var parent: Statement?
    ): Statement
