package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ConditionalExpression(
    var condition: Statement?,
    var trueTree: Statement?,
    var falseTree: Statement?
) :Statement
