package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.SimpleDeclaration

data class DeclarationWithParent(
    var declaration: SimpleDeclaration,
    var parent: Statement?,
    override val type: String = "DeclarationWithParent"
) : Statement
