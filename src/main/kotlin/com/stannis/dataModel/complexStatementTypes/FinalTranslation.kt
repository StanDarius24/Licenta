package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionDeclarator
import com.stannis.dataModel.statementTypes.FunctionDefinition
import com.stannis.dataModel.statementTypes.LinkageSpecification

data class FinalTranslation(
    override val type: String = "FinalTranslation",
    var directives: ArrayList<String>?,
    var globalDeclaration: ArrayList<DeclarationWithParent>?,
    var internDeclaration: ArrayList<DeclarationWithParent>?,
    var methodsWithFunctionCalls: ArrayList<FunctionDefinition>?,
    var functionCallsWithoutImplementation: ArrayList<FunctionDeclarator>?,
    var classList: ArrayList<ComplexCompositeTypeSpecifier>?,
    var linkageSpecification: ArrayList<LinkageSpecification>?
    ) : Statement
