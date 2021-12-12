package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ContinueStatement(
    var expression: String?
): Statement
