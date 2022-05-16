package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.DeclarationSpecifierParent
import com.stannis.dataModel.Statement

data class ElaboratedTypeSpecifier(
    override val `$type`: String? = "ElaboratedTypeSpecifier",
    var name: String?
) : Statement, DeclarationSpecifierParent
