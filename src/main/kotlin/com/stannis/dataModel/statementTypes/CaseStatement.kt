package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CaseStatement(
    var expression: String?,
    override val type: String? = "CaseStatement") :
    Statement
