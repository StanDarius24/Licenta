package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement

data class TypeIdExpression(
    override val `$type`: String? = "TypeIdExpression",
    var typeId: Arguments?
) : Statement, Arguments
