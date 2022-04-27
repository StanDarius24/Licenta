package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ConversionName(
    override val `$type`: String = "ConversionName",
    var typeId: Statement?
    ): Statement
