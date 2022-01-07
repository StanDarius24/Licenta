package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class Enumerator(
    var name: String?,
    var value: String?,
    override val type: String? = "Enumerator"
): Statement