package com.stannis.linker

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.dataModel.DeclarationParent
import com.stannis.dataModel.Statement
import com.stannis.dataModel.complexStatementTypes.ComplexCompositeTypeSpecifier
import com.stannis.dataModel.complexStatementTypes.RepositoryModel
import com.stannis.dataModel.complexStatementTypes.DeclarationWithClass
import com.stannis.dataModel.complexStatementTypes.ClassOrHeaderWithPath
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
        ProjectVcxprojComplexRegistry.parsedFiles.forEach { complexFinalTranslation ->
            run {
                vcxprojStructure = complexFinalTranslation.vcxprojStructure
                solveComplexFinalTranslation(complexFinalTranslation)
            }
        }
    }

    private fun solveComplexFinalTranslation(complexFinalTranslation: RepositoryModel) {
        complexFinalTranslation.listOfHeaderFiles!!.forEach { translationWithPath ->
            run { solveTranslationWithPath(translationWithPath) }
        }
    }

    private fun solveTranslationWithPath(
        classOrHeaderWithPath: ClassOrHeaderWithPath,
    ) {
        if (classOrHeaderWithPath.classOrHeader.classList != null) {
            classOrHeaderWithPath.classOrHeader.classList!!.forEach { classDecl ->
                run {
                    solveClassDeclaration(classDecl, classOrHeaderWithPath)
                    if (listOfNewDecl != null) {
                        listOfNewDecl!!.forEach { (t, u) ->
                            run {
                                classDecl.our_class.declarations!!.remove(t as DeclarationParent)
                                classDecl.our_class.declarations!!.add(u as DeclarationParent)
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
        classOrHeaderWithPath: ClassOrHeaderWithPath,
    ) {
        if (classDecl.our_class.declarations != null) {
            classDecl.our_class.declarations!!.forEach { declarationInClass ->
                run {
                    if (declarationInClass is SimpleDeclaration) {
                        solveSimpleDeclaration(
                            declarationInClass,
                            classOrHeaderWithPath
                        )
                    }
                }
            }
        }
    }

    private fun solveSimpleDeclaration(
        declarationInClass: SimpleDeclaration,
        classOrHeaderWithPath: ClassOrHeaderWithPath
    ) {
        if (declarationInClass.declSpecifier is NamedTypeSpecifier) {
            if ((declarationInClass.declSpecifier as NamedTypeSpecifier).name is Name) {
                if (!PRIMITIVETYPES.contains(
                        ((declarationInClass.declSpecifier as NamedTypeSpecifier).name as Name).name
                    )
                ) {
                    checkInternDeclaration(
                        classOrHeaderWithPath,
                        ((declarationInClass.declSpecifier as NamedTypeSpecifier).name as Name)
                            .name,
                        declarationInClass
                    )
                }
            }
        }
    }

    private fun checkInternDeclaration(
        classOrHeaderWithPath: ClassOrHeaderWithPath,
        name: String?,
        declarationInClass: SimpleDeclaration
    ) {
        var internDefinition = false
        if (classOrHeaderWithPath.classOrHeader.globalDeclaration != null) {
            classOrHeaderWithPath.classOrHeader.globalDeclaration!!.forEach { declarationWithParent
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
                                    declarationWithParent = declarationWithParent,
                                    linkedClass = Name(name = "Intern Class Call")
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
                        ClassToDeclarationLinker.findClass(classOrHeaderWithPath, declarationInClass, vcxprojStructure)
                    }
                }
            }
        }
    }
}
