package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.DeclarationWithParent
import com.stannis.dataModel.complexStatementTypes.FunctionCallWithDeclaration
import com.stannis.dataModel.statementTypes.*
import com.stannis.parser.metrics.Metrics

object FunctionDefinitionRegistry {

    val listFromTranslationUnit: ArrayList<FunctionDefinition> by lazy { ArrayList() }

    val listOfDefinitionOOP: ArrayList<FunctionDefinition> by lazy { ArrayList() }

    fun resolveWhenStatementIsFunctionCall(
        statement: FunctionCalls,
        newFunctionDefinition: FunctionDefinition
    ) {
        if (!checkInternDeclaration(statement, newFunctionDefinition)) {
            if (!checkGlobalDeclaration(statement, newFunctionDefinition)) {
                if (!checkMethodArgument(statement, newFunctionDefinition)) {
                    if (!checkInternMethodCall(statement, newFunctionDefinition)) {
                        libraryMethodCall(statement, newFunctionDefinition)
                    }
                }
            }
        }
    }

    private fun checkInternMethodCall(
        statement: FunctionCalls,
        newFunctionDefinition: FunctionDefinition
    ): Boolean {
        var bool1 = false
        FunctionDeclaratorRegistry.list.iterator().forEachRemaining { functionDeclarator ->
            run {
                if (statement.name is IdExpression) {
                    if (functionDeclarator.name == (statement.name as IdExpression).expression) {
                        bool1 = true
                        val decl =
                            DeclarationWithParent(
                                declaration =
                                    SimpleDeclaration(declarators = null, declSpecifier = null),
                                parent = Name(name = "Internal Method Declaration")
                            )
                        val functionCallWithDeclaration =
                            FunctionCallWithDeclaration(
                                functionCalls = statement,
                                declaration = decl,
                                complexClass = null
                            )
                        newFunctionDefinition.addToBody(functionCallWithDeclaration)
                    }
                }
            }
        }
        return bool1
    }

    private fun libraryMethodCall(
        statement: FunctionCalls,
        newFunctionDefinition: FunctionDefinition
    ) {
        // check data
        println()
    }

    private fun checkMethodArgument(
        statement: FunctionCalls,
        newFunctionDefinition: FunctionDefinition
    ): Boolean {
        var bool1 = false
        if (newFunctionDefinition.declarator!![0].parameter != null) {
            newFunctionDefinition.declarator!![0].parameter!!.iterator().forEachRemaining {
                parameter ->
                run {
                    bool1 =
                        checkIfParametersDeclaration(statement, newFunctionDefinition, parameter)
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
                if (
                    ((statement.name as FieldReference).fieldOwner as IdExpression).expression
                        is Name
                ) {
                    if (parameter.declarator is Declarator) {
                        if (
                            (parameter.declarator as Declarator).name!! ==
                                (((statement.name as FieldReference).fieldOwner as IdExpression)
                                        .expression as Name)
                                    .name
                        ) {
                            val functionCallWithDeclaration =
                                FunctionCallWithDeclaration(
                                    functionCalls = statement,
                                    declaration = parameter,
                                    complexClass = null
                                )
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
        SimpleDeclarationRegistry.globalDeclaration.iterator().forEachRemaining { declaration ->
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
        return bool1
    }

    private fun checkInternDeclaration(
        statement: FunctionCalls,
        newFunctionDefinition: FunctionDefinition
    ): Boolean {
        var bool1 = false
        SimpleDeclarationRegistry.internDeclaration.iterator().forEachRemaining { declaration ->
            run {
                if (declaration.parent is FunctionDefinition) {
                    if (
                        (declaration.parent as FunctionDefinition).declarator?.get(0)
                            is FunctionDeclarator
                    ) {
                        if (
                            ((declaration.parent as FunctionDefinition).declarator?.get(0)
                                    as FunctionDeclarator)
                                .name
                                ?.equals(
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
        return bool1
    }

    private fun verifyIfFunctionCallDeclaration(
        statement: FunctionCalls,
        declaration: DeclarationWithParent,
        newFunctionDefinition: FunctionDefinition,
        boolean: Boolean
    ): Boolean {
        var bool = false
        if (declaration.declaration.declarators != null) {
            declaration.declaration.declarators!!.iterator().forEachRemaining { declarator ->
                run {
                    if (
                        (declarator as Declarator).name.equals(getStatementName(statement, boolean))
                    ) {
                        val functionCallWithDeclaration =
                            FunctionCallWithDeclaration(
                                functionCalls = statement,
                                declaration = declarator,
                                complexClass = null
                            )
                        newFunctionDefinition.addToBody(functionCallWithDeclaration)
                        bool = true
                    }
                }
            }
        }
        return bool
    }

    private fun getStatementName(statement: FunctionCalls, boolean: Boolean): String? {
        if (statement.name is FieldReference) {
            if ((statement.name as FieldReference).fieldOwner is IdExpression) {
                if (
                    ((statement.name as FieldReference).fieldOwner as IdExpression).expression
                        is Name
                ) {
                    return (((statement.name as FieldReference).fieldOwner as IdExpression)
                            .expression as Name)
                        .name
                } else if (
                    ((statement.name as FieldReference).fieldOwner as IdExpression).expression is
                        QualifiedName && boolean
                ) {
                    if (
                        (((statement.name as FieldReference).fieldOwner as IdExpression).expression
                                as QualifiedName)
                            .lastName is Name
                    ) {
                        return ((((statement.name as FieldReference).fieldOwner as IdExpression)
                                    .expression as QualifiedName)
                                .lastName as Name)
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

    fun addToList(element: FunctionDefinition) {
        Metrics.solveFunctionDefinition(element, false)
        if ((element.declarator?.get(0) as FunctionDeclarator).name !is QualifiedName) {
            if (element.body!![0] is CompoundStatement) {
                element.body!!.removeAt(0)
            }
            listFromTranslationUnit.add(element)
        } else {
            checkFunctionParent(element)
        }
    }

    private fun checkFunctionParent(element: FunctionDefinition) {
        if(!NameSpaceRegistry.checkInNameSpace(element)){
            CompositeTypeRegistry.checkInClass(element)
        }
    }

}
