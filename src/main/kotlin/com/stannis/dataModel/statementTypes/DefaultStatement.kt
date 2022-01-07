package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class DefaultStatement(
    var expression: String?,
    override val type: String? = "DefaultStatement"
):Statement
