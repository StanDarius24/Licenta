package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class TypeIdExpression(
    var typeId: Statement?,
    override val type: String? = "TypeIdExpression"
): Statement
