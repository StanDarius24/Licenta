package com.stannis.function

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.dataModel.complexStatementTypes.ClassOrHeader

object TranslationUnitRegistry {

    lateinit var listOfDirectives: List<String>

    fun createTranslationUnit(boolean: Boolean) {
        val classOrHeader =
            ClassOrHeader(directives = listOfDirectives as ArrayList, classList = null, globalDeclaration = null, internDeclaration = null, functionCallsWithoutImplementation = null, linkageSpecification = null, methodsWithFunctionCalls = null, namespace = null)
        classOrHeader.globalDeclaration = SimpleDeclarationRegistry.globalDeclaration
        classOrHeader.internDeclaration = SimpleDeclarationRegistry.internDeclaration
        classOrHeader.methodsWithFunctionCalls =
            FunctionDefinitionRegistry.listOfComplexFunctionCalls
        classOrHeader.functionCallsWithoutImplementation = FunctionDeclaratorRegistry.list
        classOrHeader.classList = CompositeTypeRegistry.list
        classOrHeader.linkageSpecification = ExternDefinitionRegistry.listOfExtern
        classOrHeader.namespace = NameSpaceRegistry.listOfNameSpace
        ProjectVcxprojComplexRegistry.addFinalTranslation(classOrHeader, boolean)
    }

    fun clearAllData() {
        NameSpaceRegistry.listOfNameSpace = null
        SimpleDeclarationRegistry.internDeclaration = null
        SimpleDeclarationRegistry.globalDeclaration = null
        FunctionDefinitionRegistry.listOfComplexFunctionCalls = null
        FunctionDeclaratorRegistry.list = null
        CompositeTypeRegistry.list = null
        ExternDefinitionRegistry.listOfExtern = null
    }
}
