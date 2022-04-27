package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement

data class DeclarationWithClass(
    override val type: String = "DeclarationWithClass",
    var declarationWithParent: DeclarationWithParent?,
    var linkedClass: Statement?
): Statement
