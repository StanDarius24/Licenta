package com.stannis.function

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.dataModel.complexStatementTypes.FinalTranslation

object TranslationUnitRegistry {

    lateinit var listOfDirectives: List<String>

    fun createTranslationUnit() {
        val finalTranslation =
            FinalTranslation(listOfDirectives as ArrayList, null, null, null, null, null, null)
        finalTranslation.globalDeclaration = SimpleDeclarationRegistry.globalDeclaration
        finalTranslation.internDeclaration = SimpleDeclarationRegistry.internDeclaration
        finalTranslation.methodsWithFunctionCalls =
            FunctionDefinitionRegistry.listOfComplexFunctionCalls
        finalTranslation.functionCallsWithoutImplementation = FunctionDeclaratorRegistry.list
        finalTranslation.classList = CompositeTypeRegistry.list
        finalTranslation.linkageSpecification = ExternDefinitionRegistry.listOfExtern
        ProjectVcxprojComplexRegistry.addFinalTranslation(finalTranslation)
    }

    fun clearAllData() {
        SimpleDeclarationRegistry.internDeclaration = null
        SimpleDeclarationRegistry.globalDeclaration = null
        FunctionDefinitionRegistry.listOfComplexFunctionCalls = null
        FunctionDeclaratorRegistry.list = null
        CompositeTypeRegistry.list = null
        ExternDefinitionRegistry.listOfExtern = null
    }
}
