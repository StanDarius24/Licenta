package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.DeclarationParent
import com.stannis.dataModel.Statement

data class ProblemDeclaration(
    override val `$type`: String? = "ProblemDeclaration",
    var expression: String?
) : Statement, DeclarationParent
