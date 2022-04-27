package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class DefaultStatement(
    override val `$type`: String? = "DefaultStatement"
) : Statement
