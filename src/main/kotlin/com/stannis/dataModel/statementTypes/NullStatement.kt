package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NullStatement(
    var expression: String?,
    override val type: String? = "NullStatement") :
    Statement
