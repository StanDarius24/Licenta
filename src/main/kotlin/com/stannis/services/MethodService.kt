package com.stannis.services

import com.stannis.dataModel.Antet
import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement

class MethodService {
    fun createMethod(): Method {
        return Method(null, null, null, null, null)
    }

    fun addDeclaration(method: Method, declaration: Declaration) {
        if(method.declarations == null) {
            method.declarations = ArrayList()
        }
        method.declarations!!.add(declaration)
    }

    fun addStatement(method: Method, statement: Statement) {
        if(method.statements == null) {
            method.statements = ArrayList()
        }
        method.statements!!.add(statement)
    }

    fun setAntet(method: Method, antet: Antet) {
        method.antet = antet
    }
}