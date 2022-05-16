package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.DeclarationParent
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.SimpleDeclaration

data class DeclarationWithParent(
    override val `$type`: String = "DeclarationWithParent",
    var declaration: SimpleDeclaration,
    var parent: DeclarationParent?
) : Statement
