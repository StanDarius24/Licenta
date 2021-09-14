package com.stannis.services.forDataModel

import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Method

class MethodService {

    private var method = Method(null, null, null, null, null, null)

    fun reinit() {
        method = Method(null, null, null, null, null, null)
    }

    fun addMethod(content: String) {
        method.content = content
    }

    fun addMethodName(name: String) {
        method.name = name
   }

    fun addReturnType(returnType: String) {
        method.returnType = returnType
    }

    fun addAntet(declaration: Declaration) {
        if(method.antet.isNullOrEmpty()) {
            val x = ArrayList<Declaration>()
            x.add(declaration)
            method.antet = x
        } else {
            method.antet!!.add(declaration)
        }
    }

    fun addDeclaration(declaration: Declaration) {
        method.declarations?.add(declaration)
    }

    fun addContent(content: String) {
        method.content = content
    }

    fun getMethod() : Method {
        return method
    }
}