package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ElaboratedTypeSpecifier(
    var name: String?,
    override val type: String? = "ElaboratedTypeSpecifier"
) : Statement
