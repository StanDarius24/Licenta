package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class SimpleTypeTemplateParameter(
    val expression: String?
): Statement
