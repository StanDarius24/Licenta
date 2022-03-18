package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement

data class FunctionCallWithDeclaration(
    var functionCalls: Statement?,
    var declaration: Statement?,
    var complexClass: ComplexCompositeTypeSpecifier?,
    override val type: String? = "FunctionCallWithDeclaration"
) : Statement
