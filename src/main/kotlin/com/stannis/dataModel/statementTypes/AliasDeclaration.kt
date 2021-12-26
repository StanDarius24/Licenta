package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class AliasDeclaration(
    var aliasName: String?,
    var mappingTypeId: String?
): Statement
