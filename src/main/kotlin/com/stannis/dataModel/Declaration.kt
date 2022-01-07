package com.stannis.dataModel

import com.stannis.dataModel.statementTypes.FunctionCall

data class Declaration(
    var name: String?,
    var returnType: String?,
    var pointer: Boolean?,
    var function: FunctionCall?,
    var arraySize: Int?,
    var modifier: String?,
    override val type: String? = "Declaration"
): Statement
