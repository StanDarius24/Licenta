package com.stannis.services.forDataModel

import com.stannis.dataModel.Declaration

class DeclarationService {

    private var declaration = Declaration(null, null, null)

    fun addDeclarationName(name: String) {
        declaration = Declaration(null, name, null)
    }

    fun addDeclarationReturnType(returnType: String) {
        declaration.returnType = returnType
    }

    fun declarationPointer() {
        declaration.pointer = true
    }

    fun getDeclaration(): Declaration {
        return declaration
    }

}