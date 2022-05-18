package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.*

data class TypeId(
    override val `$type`: String? = "TypeId",
    var declSpecifier: DeclarationSpecifierParent?,
    var abstractDeclaration: DeclaratorInterface?
) : Statement, Arguments
