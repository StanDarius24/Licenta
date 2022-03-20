package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.FinalTranslation

object TranslationUnitRegistry {

    fun createTranslationUnit() {
        val finalTranslation = FinalTranslation(null, null, null, null, null)
        finalTranslation.globalDeclaration = SimpleDeclarationRegistry.globalDeclaration
        finalTranslation.internDeclaration = SimpleDeclarationRegistry.internDeclaration
        finalTranslation.methodsWithFunctionCalls =
            FunctionDefinitionRegistry.listOfComplexFunctionCalls
        finalTranslation.functionCallsWithoutImplementation = FunctionDeclaratorRegistry.list
    }
}
