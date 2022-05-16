package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.DeclarationSpecifierParent
import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement

data class NamedTypeSpecifier(
    override val `$type`: String? = "NamedTypeSpecifier",
    var name: NameInterface?
) : Statement, DeclarationSpecifierParent
