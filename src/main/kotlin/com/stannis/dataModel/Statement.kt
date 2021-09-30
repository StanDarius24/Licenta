package com.stannis.dataModel

import com.stannis.dataModel.statementTypes.FunctionCall

interface Statement {
    fun add(data: String)
    fun add(data: FunctionCall)
}