package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class LambdaExpression(
    var captureDefault: Statement?,
    var captures: Statement?,
    var declarator: Statement?,
    var body: Statement?,
    var closureTypeName: Statement?,
    var implicitFunctionCallName: Statement?,
    override val type: String? = "LambdaExpression",
    ): Statement
