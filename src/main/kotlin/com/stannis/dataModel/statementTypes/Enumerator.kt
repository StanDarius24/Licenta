package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class Enumerator(
    var name: Statement?,
    var value: Statement?,
    override val type: String? = "Enumerator"
): Statement