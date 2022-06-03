package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CyclomaticComplexityModel(
    override val `$type`: String? = "CyclomaticComplexityModel",
    val cyclomaticComplexity: Int = 1,
    val reference: Map<String, String>?,
):Statement
