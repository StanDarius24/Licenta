package com.stannis.services.forDataModel

import com.stannis.dataModel.Method

class MethodService {

    private var declarationService: DeclarationService = DeclarationService()
    private var method = Method(null, null, null, null, null, null)

    fun addMethod(content: String) {
        method.content = content
    }

    fun addMethodName(name: String) {
        method.name = name
   }

    fun addReturnType(returnType: String) {
        method.returnType = returnType
    }

    fun addAntet() {
        method.antet?.add(declarationService.getDeclaration())
    }

    fun addDeclaration() {
        method.declarations?.add(declarationService.getDeclaration())
    }
}