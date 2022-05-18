package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.DeclaratorInterface
import com.stannis.dataModel.Statement

data class Declarator(
    override val `$type`: String? = "Declarator",
    var modifier: String?,
    var name: String?,
    var initialization: Statement?
    ) : Statement, DeclaratorInterface
