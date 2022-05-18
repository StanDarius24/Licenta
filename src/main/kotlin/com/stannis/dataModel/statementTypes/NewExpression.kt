package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement

data class NewExpression(
    override val `$type`: String? = "NewExpression",
    var typeId: TypeId?,
    var initializer: Arguments?
) : Statement, Arguments
