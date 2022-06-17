package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.DeclWithParent
import com.stannis.dataModel.complexStatementTypes.FunctionCallsWithDeclaration
import com.stannis.dataModel.statementTypes.*

object FunctionCallsRegistry {

    fun generateFunctionWithParent(
        element: FunctionCalls,
        functionDefinition: FunctionDefinition
    ): FunctionCallsWithDeclaration? {
        DeclarationRegistry.listOfDeclaration.forEach { elementDeclaration ->
            run {
                if (elementDeclaration.declaration is DeclarationStatement) {
                    if (
                        (elementDeclaration.declaration as DeclarationStatement).declarations !=
                            null &&
                            (elementDeclaration.declaration as DeclarationStatement)
                                .declarations!!
                                .isNotEmpty()
                    ) {
                        if (
                            (elementDeclaration.declaration as DeclarationStatement).declarations!![
                                0]
                                is SimpleDeclaration
                        ) {
                            val simpleDecl =
                                (elementDeclaration.declaration as DeclarationStatement)
                                    .declarations!![0]
                                    as SimpleDeclaration
                            if (
                                simpleDecl.declarators != null &&
                                    simpleDecl.declarators!!.isNotEmpty()
                            ) {
                                if (simpleDecl.declarators!![0] is Declarator) {
                                    if (
                                        (element.name is FieldReference) &&
                                            (element.name as FieldReference).fieldOwner is
                                                IdExpression
                                    ) {
                                        if (
                                            (simpleDecl.declarators!![0] as Declarator)
                                                .name
                                                .equals(
                                                    ((element.name as FieldReference).fieldOwner
                                                            as IdExpression)
                                                        .expression!!
                                                        .getWrittenName()
                                                )
                                        ) {
                                            return FunctionCallsWithDeclaration(
                                                functionCalls = element,
                                                declWithParent = elementDeclaration.declaration
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (functionDefinition.declarator != null && functionDefinition.declarator!!.isNotEmpty()) {
            if (
                functionDefinition.declarator!![0].parameter != null &&
                    functionDefinition.declarator!![0].parameter!!.isNotEmpty()
            ) {
                functionDefinition.declarator!![0].parameter!!.forEach { parameterDeclaration ->
                    run {
                        if (
                            (element.name is FieldReference) &&
                                parameterDeclaration.declarator is Declarator &&
                                (element.name as FieldReference).fieldOwner is IdExpression &&
                                (parameterDeclaration.declarator as Declarator).name ==
                                    ((element.name as FieldReference).fieldOwner as IdExpression)
                                        .expression!!
                                        .getWrittenName()
                        ) {
                            return FunctionCallsWithDeclaration(
                                functionCalls = element,
                                declWithParent =
                                    DeclWithParent(
                                        declaration = parameterDeclaration,
                                        parent = null
                                    )
                            )
                        }
                    }
                }
            }
        }

        SimpleDeclarationRegistry.globalDeclaration.forEach { declarationWithParent ->
            run {
                if (
                    element.name is FieldReference &&
                        (element.name as FieldReference).fieldOwner is IdExpression &&
                        (declarationWithParent.declaration.declarators!![0] as Declarator).name ==
                            ((element.name as FieldReference).fieldOwner as IdExpression)
                                .expression!!
                                .getWrittenName()
                ) {
                    return FunctionCallsWithDeclaration(
                        functionCalls = element,
                        declWithParent =
                            DeclWithParent(
                                declaration = declarationWithParent.declaration,
                                parent = null
                            )
                    )
                }
            }
        }
        return null
    }

    val listOfFunctionCalls: ArrayList<FunctionCalls> by lazy { ArrayList() }
}
