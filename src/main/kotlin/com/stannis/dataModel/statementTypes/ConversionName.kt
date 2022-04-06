package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ConversionName(
    var typeId: Statement?,
    override val type: String = "ConversionName"
    ): Statement
