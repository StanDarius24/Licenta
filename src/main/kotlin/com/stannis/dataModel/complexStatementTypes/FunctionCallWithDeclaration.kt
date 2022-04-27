package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement

data class FunctionCallWithDeclaration(
    override val type: String? = "FunctionCallWithDeclaration",
    var functionCalls: Statement?,
    var declaration: Statement?,
    var complexClass: ComplexCompositeTypeSpecifier?
) : Statement
