package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionDefinition
import com.stannis.dataModel.statementTypes.SimpleDeclaration

data class DeclarationWithParent(
    var declaration: SimpleDeclaration,
    var parent: FunctionDefinition?,
    override val type: String = "DeclarationWithParent"
): Statement
