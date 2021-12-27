package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class ParameterDeclaration(
    var declSpecifier: String?,
    var declarator: String?
): Statement
