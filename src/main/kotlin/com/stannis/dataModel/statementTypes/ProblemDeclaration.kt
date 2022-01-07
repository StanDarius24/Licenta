package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ProblemDeclaration(
    var expression: String?,
    override val type: String? = "ProblemDeclaration"
): Statement
