package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CatchHandler(
    var body: Statement?
): Statement
