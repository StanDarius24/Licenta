package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionDeclarator
import com.stannis.dataModel.statementTypes.FunctionDefinition

data class FinalTranslation(
    var directives: ArrayList<Statement>?,
    var globalDeclaration: ArrayList<DeclarationWithParent>?,
    var internDeclaration: ArrayList<DeclarationWithParent>?,
    var methodsWithFunctionCalls: ArrayList<FunctionDefinition>?,
    var functionCallsWithoutImplementation: ArrayList<FunctionDeclarator>?,
    override val type: String = "FinalTranslation"
) : Statement
