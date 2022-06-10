package com.stannis.function

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.dataModel.complexStatementTypes.ClassOrHeader
import com.stannis.dataModel.statementTypes.*
import com.stannis.services.astNodes.TranslationUnitService

object TranslationUnitRegistry {

    lateinit var listOfDirectives: List<String>

    fun createTranslationUnit(boolean: Boolean) {
        fixElements()
        val classOrHeader =
            ClassOrHeader(
                directives = listOfDirectives as ArrayList,
                classList = null,
                globalDeclaration = null,
                internDeclaration = null,
                functionCallsWithoutImplementation = null,
                linkageSpecification = null,
                methodsWithFunctionCalls = null,
                namespaces = null,
                externalMethods = null
            )
        classOrHeader.globalDeclaration = SimpleDeclarationRegistry.globalDeclaration
        classOrHeader.internDeclaration = DeclarationRegistry.listOfInternDeclarations
        classOrHeader.methodsWithFunctionCalls = FunctionDefinitionRegistry.listOfDefinitionOOP
        classOrHeader.classList = CompositeTypeRegistry.list
        classOrHeader.linkageSpecification = ExternDefinitionRegistry.listOfExtern
        classOrHeader.namespaces = NameSpaceRegistry.listOfNameSpace
        classOrHeader.externalMethods = ExternalRegistry.listOfExternal
        ProjectVcxprojComplexRegistry.addFinalTranslation(classOrHeader, boolean)
    }

    private fun fixElements() {
        if (TranslationUnitService.translationUnitCache.listOfDeclarations != null) {
            TranslationUnitService.translationUnitCache.listOfDeclarations!!.forEach { element ->
                run {
                    when (element) {
                        is FunctionDefinition -> {
                            FunctionDefinitionRegistry.addToList(element)
                        }
                        is NameSpace -> {
                            NameSpaceRegistry.listOfNameSpace.add(element)
                        }
                        is SimpleDeclaration -> {
                            if (element.declSpecifier is NamedTypeSpecifier) {
                                SimpleDeclarationRegistry.addToList(element, null)
                            }
                        }
                    }
                }
            }
        }
    }

    fun clearAllData() {
        NameSpaceRegistry.listOfNameSpace = ArrayList()
        DeclarationRegistry.listOfDeclaration = ArrayList()
        DeclarationRegistry.listOfInternDeclarations = ArrayList()
        SimpleDeclarationRegistry.globalDeclaration = ArrayList()
        FunctionDeclaratorRegistry.list = ArrayList()
        FunctionDefinitionRegistry.listFromTranslationUnit = ArrayList()
        FunctionDefinitionRegistry.listOfDefinitionOOP = ArrayList()
        CompositeTypeRegistry.list = null
        ExternDefinitionRegistry.listOfExtern = ArrayList()
    }
}
