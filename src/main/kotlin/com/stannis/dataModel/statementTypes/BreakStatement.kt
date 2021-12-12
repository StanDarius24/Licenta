package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class BreakStatement(
    var expression: String?
): Statement
