package com.stannis.linker

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.dataModel.complexStatementTypes.ComplexCompositeTypeSpecifier
import com.stannis.dataModel.complexStatementTypes.ComplexFinalTranslation
import com.stannis.dataModel.complexStatementTypes.TranslationWithPath
import com.stannis.dataModel.statementTypes.EnumerationSpecifier
import com.stannis.dataModel.statementTypes.Name
import com.stannis.dataModel.statementTypes.NamedTypeSpecifier
import com.stannis.dataModel.statementTypes.SimpleDeclaration

object LinkClassDeclarationsService {

    private var PRIMITIVESTYPES =
        listOf(
            "char",
            "unsigned char",
            "signed char",
            "int",
            "unsigned int",
            "signed int",
            "short int",
            "unsigned short int",
            "signed short int",
            "long int",
            "signed long int",
            "long long int",
            "unsigned long long int",
            "float",
            "double",
            "long double",
            "wchar_t",
            "uint8_t"
        )

    fun declarationsToClass() {
        ProjectVcxprojComplexRegistry.parsedList.forEach { complexFinalTranslation ->
            run { solveComplexFinalTranslation(complexFinalTranslation) }
        }
    }

    private fun solveComplexFinalTranslation(complexFinalTranslation: ComplexFinalTranslation) {
        complexFinalTranslation.listOfTranslation.forEach { translationWithPath ->
            run { solveTranslationWithPath(translationWithPath, complexFinalTranslation) }
        }
    }

    private fun solveTranslationWithPath(
        translationWithPath: TranslationWithPath,
        complexFinalTranslation: ComplexFinalTranslation
    ) {
        if (translationWithPath.finalTranslation.classList != null) {
            translationWithPath.finalTranslation.classList!!.forEach { classDecl ->
                run {
                    solveClassDeclaration(classDecl, translationWithPath, complexFinalTranslation)
                }
            }
        }
    }

    private fun solveClassDeclaration(
        classDecl: ComplexCompositeTypeSpecifier,
        translationWithPath: TranslationWithPath,
        complexFinalTranslation: ComplexFinalTranslation
    ) {
        if (classDecl.our_class.declarations != null) {
            classDecl.our_class.declarations!!.forEach { declarationInClass ->
                run {
                    if (declarationInClass is SimpleDeclaration) {
                        solveSimpleDeclaration(
                            declarationInClass,
                            translationWithPath,
                            complexFinalTranslation
                        )
                    }
                }
            }
        }
    }

    private fun solveSimpleDeclaration(
        declarationInClass: SimpleDeclaration,
        complexFinalTranslation: TranslationWithPath,
        complexFinalTranslation1: ComplexFinalTranslation
    ) {
        if (declarationInClass.declSpecifier is NamedTypeSpecifier) {
            if ((declarationInClass.declSpecifier as NamedTypeSpecifier).name is Name) {
                if (!PRIMITIVESTYPES.contains(
                        ((declarationInClass.declSpecifier as NamedTypeSpecifier).name as Name).name
                    )
                ) {
                    checkInternDeclaration(
                        complexFinalTranslation,
                        ((declarationInClass.declSpecifier as NamedTypeSpecifier).name as Name).name
                    )
                }
            }
        }
    }

    private fun checkInternDeclaration(
        complexFinalTranslation: TranslationWithPath,
        name: String?
    ) {
        var internDefinition = false
        if (complexFinalTranslation.finalTranslation.globalDeclaration != null) {
            complexFinalTranslation.finalTranslation.globalDeclaration!!.forEach {
                declarationWithParent ->
                run {
                    if (declarationWithParent.declaration.declSpecifier is EnumerationSpecifier) {
                        if ((declarationWithParent.declaration.declSpecifier as
                                    EnumerationSpecifier)
                                .name == name
                        ) {
                            internDefinition = true
                        }
                    }
                    if(!internDefinition) {
                        println()
                    }
                }
            }
        }
    }
}
