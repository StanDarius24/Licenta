package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class GotoStatement(
    var jumpTo: String?
): Statement
