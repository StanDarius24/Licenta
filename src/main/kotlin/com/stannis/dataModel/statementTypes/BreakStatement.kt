package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class BreakStatement(var expression: String?, override val type: String? = "BreakStatement") :
    Statement
