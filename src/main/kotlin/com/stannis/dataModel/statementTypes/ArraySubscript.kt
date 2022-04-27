package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ArraySubscript(
    override val `$type`: String? = "ArraySubscript",
    var arrayValue: String?,
    var index: String?
) : Statement
