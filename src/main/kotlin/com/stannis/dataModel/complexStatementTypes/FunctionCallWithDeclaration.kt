package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.BodyParent
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionCalls

data class FunctionCallWithDeclaration(
    override val `$type`: String? = "FunctionCallWithDeclaration",
    var functionCalls: FunctionCalls?,
    var declaration: Statement?,
    var complexClass: ComplexCompositeTypeSpecifier?
) : Statement, BodyParent
