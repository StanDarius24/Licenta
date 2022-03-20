package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.DeclarationWithParent
import com.stannis.dataModel.complexStatementTypes.FunctionCallWithDeclaration
import com.stannis.dataModel.statementTypes.*
import com.stannis.services.astNodes.FunctionDefinitionService
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition

object FunctionDefinitionRegistry {

    var list: ArrayList<FunctionDefinition>? = null

    var listOfComplexFunctionCalls: ArrayList<FunctionDefinition>? = null

    private fun addToComplexFunctions(functionDefinition: FunctionDefinition) {
        if (listOfComplexFunctionCalls == null) {
            listOfComplexFunctionCalls = ArrayList()
        }
        listOfComplexFunctionCalls!!.add(functionDefinition)
        println()
    }

    fun clearList() {
        list = null
    }

    fun addToList(data: FunctionDefinition) {
        if (list == null) {
            list = ArrayList()
        }
        list!!.add(data)
    }

    private fun resolveWhenStatementIsFunctionCall(
        statement: FunctionCalls,
        newFunctionDefinition: FunctionDefinition
    ) {
        if (!checkInternDeclaration(statement, newFunctionDefinition)) {
            if (!checkGlobalDeclaration(statement, newFunctionDefinition)) {
                if (!checkMethodArgument(statement, newFunctionDefinition)) {
                    libraryMethodCall(statement, newFunctionDefinition)
                }
            }
        }
    }

    private fun libraryMethodCall(
        statement: FunctionCalls,
        newFunctionDefinition: FunctionDefinition
    ) {
        // check data
    }

    private fun checkMethodArgument(
        statement: FunctionCalls,
        newFunctionDefinition: FunctionDefinition
    ): Boolean {
        var bool1 = false
        if ((newFunctionDefinition.declarator!![0] as FunctionDeclarator).parameter != null) {
            (newFunctionDefinition.declarator!![0] as FunctionDeclarator).parameter!!.iterator()
                .forEachRemaining { parameter ->
                    run {
                        bool1 = checkIfParametersDeclaration(
                            statement, newFunctionDefinition, parameter as ParameterDeclaration
                        )
                    }
                }
        }
        return bool1
    }

    private fun checkIfParametersDeclaration(
        statement: FunctionCalls,
        newFunctionDefinition: FunctionDefinition,
        parameter: ParameterDeclaration
    ): Boolean {
        var bool1 = false
        if (statement.name is FieldReference) {
            if ((statement.name as FieldReference).fieldOwner is IdExpression) {
                if (((statement.name as FieldReference).fieldOwner as IdExpression).expression is
                        Name
                ) {
                        if (parameter.declarator is Declarator) {
                            if ((parameter.declarator as Declarator).name!!.equals(
                                    (((statement.name as FieldReference).fieldOwner as IdExpression)
                                            .expression as
                                            Name)
                                        .name
                                )
                            ) {
                                val functionCallWithDeclaration =
                                    FunctionCallWithDeclaration(statement, parameter, null)
                                newFunctionDefinition.addToBody(functionCallWithDeclaration)
                                bool1 = true
                            }
                        }
                }
            }
        }
        return bool1
    }

    private fun checkGlobalDeclaration(
        statement: FunctionCalls,
        newFunctionDefinition: FunctionDefinition
    ): Boolean {
        var bool1 = false
        if (SimpleDeclarationRegistry.globalDeclaration != null) {
            SimpleDeclarationRegistry.globalDeclaration!!.iterator().forEachRemaining { declaration
                ->
                run {
                    bool1 =
                        verifyIfFunctionCallDeclaration(
                            statement,
                            declaration,
                            newFunctionDefinition,
                            true
                        )
                }
            }
        }
        return bool1
    }

    private fun checkInternDeclaration(
        statement: FunctionCalls,
        newFunctionDefinition: FunctionDefinition
    ): Boolean {
        var bool1 = false
        if (SimpleDeclarationRegistry.internDeclaration != null) {
            SimpleDeclarationRegistry.internDeclaration!!.iterator().forEachRemaining { declaration
                ->
                run {
                    if (declaration.parent is FunctionDefinition) {
                        if ((declaration.parent as FunctionDefinition).declarator?.get(0) is
                                FunctionDeclarator
                        ) {
                            if (((declaration.parent as FunctionDefinition).declarator?.get(0) as
                                        FunctionDeclarator)
                                    .name?.equals(
                                    (newFunctionDefinition.declarator?.get(0) as FunctionDeclarator)
                                        .name
                                ) == true
                            ) {
                                bool1 =
                                    verifyIfFunctionCallDeclaration(
                                        statement,
                                        declaration,
                                        newFunctionDefinition,
                                        false
                                    )
                            }
                        }
                    }
                }
            }
        }
        return bool1
    }

    private fun verifyIfFunctionCallDeclaration(
        statement: FunctionCalls,
        declaration: DeclarationWithParent,
        newFunctionDefinition: FunctionDefinition,
        boolean: Boolean
    ): Boolean {
        var bool = false
        declaration.declaration.declarators!!.iterator().forEachRemaining { declarator ->
            run {
                if ((declarator as Declarator).name.equals(getStatementName(statement, boolean))) {
                    val functionCallWithDeclaration =
                        FunctionCallWithDeclaration(statement, declarator, null)
                    newFunctionDefinition.addToBody(functionCallWithDeclaration)
                    bool = true
                }
            }
        }
        return bool
    }

    private fun getStatementName(statement: FunctionCalls, boolean: Boolean): String? {
        if (statement.name is FieldReference) {
            if ((statement.name as FieldReference).fieldOwner is IdExpression) {
                if (((statement.name as FieldReference).fieldOwner as IdExpression).expression is
                        Name
                ) {
                    return (((statement.name as FieldReference).fieldOwner as IdExpression)
                            .expression as
                            Name)
                        .name
                } else if (((statement.name as FieldReference).fieldOwner as IdExpression)
                        .expression is
                        QualifiedName && boolean
                ) {
                    if ((((statement.name as FieldReference).fieldOwner as IdExpression)
                                .expression as
                                QualifiedName)
                            .lastName is
                            Name
                    ) {
                        return ((((statement.name as FieldReference).fieldOwner as IdExpression)
                                    .expression as
                                    QualifiedName)
                                .lastName as
                                Name)
                            .name
                    }
                }
            }
        } else if (statement.name is IdExpression) {
            if ((statement.name as IdExpression).expression is Name) {
                return ((statement.name as IdExpression).expression as Name).name
            }
        }
        return null
    }

    fun addFunctionCallToFunctionDefinition(
        functionCalls: FunctionCalls,
        parent: CPPASTFunctionDefinition
    ) {
        val parentStructure = FunctionDefinitionService.setFunction(parent)
        if (!checkInternComplex(parentStructure, functionCalls)) {
            list!!.iterator().forEachRemaining { functionDefinition ->
                run {
                    if (parentStructure == functionDefinition) {
                        resolveWhenStatementIsFunctionCall(functionCalls, parentStructure)
                    }
                }
            }
            addToComplexFunctions(parentStructure)
        }
        println(parentStructure)
    }

    private fun checkInternComplex(
        parentStructure: FunctionDefinition,
        functionCalls: FunctionCalls
    ): Boolean {
        var bool = false
        if (listOfComplexFunctionCalls != null) {
            listOfComplexFunctionCalls!!.iterator().forEachRemaining { complex ->
                run {
                    parentStructure.body = complex.body
                    if (parentStructure == complex) {
                        resolveWhenStatementIsFunctionCall(functionCalls, complex)
                        bool = true
                    }
                    parentStructure.body = null
                }
            }
        }
        return bool
    }
}
