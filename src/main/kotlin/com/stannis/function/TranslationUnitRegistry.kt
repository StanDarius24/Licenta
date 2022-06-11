package com.stannis.function

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.complexStatementTypes.ClassOrHeader
import com.stannis.dataModel.complexStatementTypes.ComplexCompositeTypeSpecifier
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
        fixOverrideMethods(classOrHeader)
        ProjectVcxprojComplexRegistry.addFinalTranslation(classOrHeader, boolean)
    }

    private fun fixClass(
        classElement: ComplexCompositeTypeSpecifier,
        classOrHeader: ClassOrHeader
    ) {
        if (classElement.our_class.declarations != null) {
            refactorClass(classElement.our_class, classOrHeader, null)
        }
    }

    private fun refactorClass(
        classElement: CompositeTypeSpecifier,
        classOrHeader: ClassOrHeader,
        nameSpace: NameSpace?
    ) {
        classElement.declarations!!.forEach { declarationParent ->
            run {
                if (nameSpace != null && declarationParent is FunctionDefinition) {
                    if (declarationParent.declarator!![0].name is QualifiedName) {
                        (declarationParent.declarator!![0].name as QualifiedName)
                            .qualifier!!
                            .forEach { qualifiedName ->
                                run {
                                    if (qualifiedName.getWrittenName() == nameSpace.name) {
                                        findMethodThatFits(
                                            declarationParent,
                                            classOrHeader,
                                            classElement.name,
                                            nameSpace
                                        )
                                    }
                                    println()
                                }
                            }
                    }
                } else {
                    if (declarationParent is FunctionDefinition) {
                        findMethodThatFits(declarationParent, classOrHeader, classElement.name, null)
                    }
                }
            }
        }
    }

    private fun fixOverrideMethods(classOrHeader: ClassOrHeader) {
        if (classOrHeader.classList != null && classOrHeader.classList!!.isNotEmpty()) {
            classOrHeader.classList!!.forEach { classElement ->
                run { fixClass(classElement, classOrHeader) }
            }
        }
        if (classOrHeader.namespaces != null && classOrHeader.namespaces!!.isNotEmpty()) {
            classOrHeader.namespaces!!.forEach { nameSpace ->
                run { fixNameSpace(classOrHeader, nameSpace) }
            }
        }
    }

    private fun fixNameSpace(classOrHeader: ClassOrHeader, nameSpace: NameSpace) {
        if (nameSpace.declarations != null && nameSpace.declarations!!.isNotEmpty()) {
            nameSpace.declarations!!.forEach { declaration ->
                run {
                    if (declaration is FunctionDefinition) {
                        println()
                    } else if (declaration is SimpleDeclaration) {
                        if (declaration.declSpecifier is CompositeTypeSpecifier) {
                            refactorClass(
                                declaration.declSpecifier as CompositeTypeSpecifier,
                                classOrHeader,
                                nameSpace
                            )
                        }
                    }
                }
            }
        }
    }

    private fun findMethodThatFits(
        declarationParent: FunctionDefinition,
        classOrHeader: ClassOrHeader,
        name: NameInterface?,
        nameSpace: NameSpace?
    ) {
        val listToRemove = ArrayList<FunctionDefinition>()
        if (classOrHeader.methodsWithFunctionCalls != null) {
            classOrHeader.methodsWithFunctionCalls!!.forEach { methodCall ->
                run {
                    if (!lookForExactName(methodCall, declarationParent, name)) {
                        lookForQualifyName(methodCall, declarationParent)
                    } else {
                        listToRemove.add(methodCall)
                    }
                }
            }
        }
        listToRemove.forEach { elementToRemove ->
            run { classOrHeader.methodsWithFunctionCalls!!.remove(elementToRemove) }
        }
        println()
    }

    private fun lookForQualifyName(
        methodCall: FunctionDefinition,
        declarationParent: FunctionDefinition
    ) {
        println()
    }

    private fun lookForExactName(
        methodCall: FunctionDefinition,
        declarationParent: FunctionDefinition,
        classOrHeader: NameInterface?
    ): Boolean {
        if (methodCall.declarator != null) {
            if (declarationParent.declarator != null) {
                if (
                    (declarationParent.declarator!![0].name as NameInterface).getWrittenName() ==
                        (methodCall.declarator!![0].name as NameInterface).getWrittenName()
                ) {
                    if (declarationParent.declarator!![0].name is QualifiedName) {
                        if (
                            (declarationParent.declarator!![0].name as QualifiedName)
                                .qualifier!!
                                .contains(classOrHeader!!)
                        ) {
                            declarationParent.body = methodCall.body
                            return true
                        }
                    } else {
                        declarationParent.body = methodCall.body
                        return true
                    }
                }
            }
        }
        return false
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
