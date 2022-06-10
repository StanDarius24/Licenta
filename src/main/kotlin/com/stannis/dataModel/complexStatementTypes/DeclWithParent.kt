package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement

data class DeclWithParent(
    var declaration: Statement?,
    var parent: Statement?,
    override val `$type`: String? = "DeclWithParent"
    ): Statement
