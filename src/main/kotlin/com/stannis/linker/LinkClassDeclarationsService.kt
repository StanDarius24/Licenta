package com.stannis.linker

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.dataModel.Statement
import com.stannis.dataModel.complexStatementTypes.ComplexCompositeTypeSpecifier
import com.stannis.dataModel.complexStatementTypes.ComplexFinalTranslation
import com.stannis.dataModel.complexStatementTypes.DeclarationWithClass
import com.stannis.dataModel.complexStatementTypes.TranslationWithPath
import com.stannis.dataModel.statementTypes.*
import com.stannis.function.ClassToDeclarationLinker
import com.stannis.parser.sln.VcxprojStructure

object LinkClassDeclarationsService {

    private var PRIMITIVETYPES =
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

    private var listOfNewDecl: Map<Statement, Statement>? = null
    private lateinit var vcxprojStructure: VcxprojStructure

    fun declarationsToClass() {
        ProjectVcxprojComplexRegistry.parsedList.forEach { complexFinalTranslation ->
            run {
                vcxprojStructure = complexFinalTranslation.vcxprojStructure
                solveComplexFinalTranslation(complexFinalTranslation)
            }
        }
    }

    private fun solveComplexFinalTranslation(complexFinalTranslation: ComplexFinalTranslation) {
        complexFinalTranslation.listOfTranslation.forEach { translationWithPath ->
            run { solveTranslationWithPath(translationWithPath) }
        }
    }

    private fun solveTranslationWithPath(
        translationWithPath: TranslationWithPath,
    ) {
        if (translationWithPath.finalTranslation.classList != null) {
            translationWithPath.finalTranslation.classList!!.forEach { classDecl ->
                run {
                    solveClassDeclaration(classDecl, translationWithPath)
                    if (listOfNewDecl != null) {
                        listOfNewDecl!!.forEach { (t, u) ->
                            run {
                                classDecl.our_class.declarations!!.remove(t)
                                classDecl.our_class.declarations!!.add(u)
                                listOfNewDecl = null
                            }
                        }
                    }
                }
            }
        }
    }

    private fun solveClassDeclaration(
        classDecl: ComplexCompositeTypeSpecifier,
        translationWithPath: TranslationWithPath,
    ) {
        if (classDecl.our_class.declarations != null) {
            classDecl.our_class.declarations!!.forEach { declarationInClass ->
                run {
                    if (declarationInClass is SimpleDeclaration) {
                        solveSimpleDeclaration(
                            declarationInClass,
                            translationWithPath
                        )
                    }
                }
            }
        }
    }

    private fun solveSimpleDeclaration(
        declarationInClass: SimpleDeclaration,
        translationWithPath: TranslationWithPath
    ) {
        if (declarationInClass.declSpecifier is NamedTypeSpecifier) {
            if ((declarationInClass.declSpecifier as NamedTypeSpecifier).name is Name) {
                if (!PRIMITIVETYPES.contains(
                        ((declarationInClass.declSpecifier as NamedTypeSpecifier).name as Name).name
                    )
                ) {
                    checkInternDeclaration(
                        translationWithPath,
                        ((declarationInClass.declSpecifier as NamedTypeSpecifier).name as Name)
                            .name,
                        declarationInClass
                    )
                }
            }
        }
    }

    private fun checkInternDeclaration(
        translationWithPath: TranslationWithPath,
        name: String?,
        declarationInClass: SimpleDeclaration
    ) {
        var internDefinition = false
        if (translationWithPath.finalTranslation.globalDeclaration != null) {
            translationWithPath.finalTranslation.globalDeclaration!!.forEach { declarationWithParent
                ->
                run {
                    if (declarationWithParent.declaration.declSpecifier is EnumerationSpecifier) {
                        if ((declarationWithParent.declaration.declSpecifier as
                                    EnumerationSpecifier)
                                .name == name
                        ) {
                            internDefinition = true
                            val declarationWithClass =
                                DeclarationWithClass(
                                    declarationWithParent,
                                    Name("Intern Class Call")
                                )
                            listOfNewDecl = if (listOfNewDecl == null) {
                                mapOf(declarationInClass to declarationWithClass)
                            } else {
                                listOfNewDecl!! +
                                        mapOf(declarationInClass to declarationWithClass)
                            }
                        }
                    }
                    if (!internDefinition) {
                        println()
                        ClassToDeclarationLinker.findClass(translationWithPath, declarationInClass, vcxprojStructure)
                    }
                }
            }
        }
    }
}
