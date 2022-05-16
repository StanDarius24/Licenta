package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionDeclarator
import com.stannis.dataModel.statementTypes.FunctionDefinition
import com.stannis.dataModel.statementTypes.LinkageSpecification
import com.stannis.dataModel.statementTypes.NameSpace

data class ClassOrHeader(
    override val `$type`: String = "ClassOrHeader",
    var directives: ArrayList<String>?,
    var globalDeclaration: ArrayList<DeclarationWithParent>?,
    var internDeclaration: ArrayList<DeclarationWithParent>?,
    var methodsWithFunctionCalls: ArrayList<FunctionDefinition>?,
    var functionCallsWithoutImplementation: ArrayList<FunctionDeclarator>?,
    var classList: ArrayList<ComplexCompositeTypeSpecifier>?,
    var linkageSpecification: ArrayList<LinkageSpecification>?,
    var namespace: ArrayList<NameSpace>?
    ) : Statement
