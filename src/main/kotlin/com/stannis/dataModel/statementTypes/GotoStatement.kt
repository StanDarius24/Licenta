package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class GotoStatement(var jumpTo: String?, override val type: String? = "GotoStatement") :
    Statement
