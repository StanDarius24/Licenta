package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class FunctionCallWithDeclaration(
    var functionCalls: Statement?,
    var declaration: Statement?,
    override val type: String? = "FunctionCallWithDeclaration"
    ): Statement
