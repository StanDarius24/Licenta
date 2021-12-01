package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CastExpression(
    var castType: String?,
    var argument: String?
): Statement
