package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement

data class DeclarationWithClass(
    var declarationWithParent: DeclarationWithParent?,
    var linkedClass: Statement?,
    override val type: String = "DeclarationWithClass"
): Statement
