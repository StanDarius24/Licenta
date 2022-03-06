package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class Name(var name: String?, override val type: String? = "Name") : Statement
