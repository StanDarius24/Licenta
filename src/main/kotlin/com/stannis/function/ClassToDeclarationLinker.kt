package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.FunctionCallWithDeclaration
import com.stannis.dataModel.complexStatementTypes.TranslationWithPath
import com.stannis.dataModel.statementTypes.*
import com.stannis.parser.sln.VcxprojStructure

object ClassToDeclarationLinker {
    fun linkClassDeclarationsToDeclarator() {
        if (FunctionDefinitionRegistry.listOfComplexFunctionCalls != null) {
            FunctionDefinitionRegistry.listOfComplexFunctionCalls!!.iterator().forEachRemaining {
                functionDefinition ->
                run {
                    if (functionDefinition.body != null) {
                        functionDefinition.body!!.iterator().forEachRemaining {
                            functionCallWithDeclaration ->
                            run {
                                CompositeTypeRegistry.list!!.iterator().forEachRemaining {
                                    complexClass ->
                                    run {
                                        if ((functionCallWithDeclaration as
                                                    FunctionCallWithDeclaration)
                                                .declaration is
                                                Declarator
                                        ) {
                                            if (((functionCallWithDeclaration.declaration as
                                                            Declarator)
                                                        .initialization as
                                                        EqualsInitializer)
                                                    .functionName is
                                                    IdExpression
                                            ) {
                                                if ((complexClass.our_class.name as Name).name
                                                        .equals(
                                                            ((((functionCallWithDeclaration
                                                                                .declaration as
                                                                                Declarator)
                                                                            .initialization as
                                                                            EqualsInitializer)
                                                                        .functionName as
                                                                        IdExpression)
                                                                    .expression as
                                                                    Name)
                                                                .name
                                                        )
                                                ) {
                                                    functionCallWithDeclaration.complexClass =
                                                        complexClass
                                                }
                                            } else if (((functionCallWithDeclaration.declaration as
                                                            Declarator)
                                                        .initialization as
                                                        EqualsInitializer)
                                                    .functionName is
                                                    FieldReference
                                            ) {
                                                println()
                                            }
                                        } else if (functionCallWithDeclaration.declaration is
                                                ParameterDeclaration
                                        ) {
                                            println() // Library Call
                                            // check library name in header file!
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun findClass(
        translationWithPath: TranslationWithPath,
        declarationInClass: SimpleDeclaration,
        vcxprojStructure: VcxprojStructure
    ) {
        println()
    }

}
