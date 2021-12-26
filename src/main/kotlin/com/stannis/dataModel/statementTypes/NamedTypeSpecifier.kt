package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class NamedTypeSpecifier(
    var name: String?
): Statement
