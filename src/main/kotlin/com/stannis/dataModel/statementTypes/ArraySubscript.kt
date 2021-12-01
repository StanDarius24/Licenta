package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ArraySubscript(
    var arrayValue: String?,
    var index: String?
): Statement
