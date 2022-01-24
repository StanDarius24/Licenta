package com.stannis.function

import com.stannis.dataModel.statementTypes.FunctionDeclarator
import com.stannis.dataModel.statementTypes.Name
import com.stannis.dataModel.statementTypes.QualifiedName

object FunctionDelegator {
    var list: ArrayList<FunctionDeclarator>? = null
    var sw = true
    fun addToList(functionDeclarator: FunctionDeclarator) {
        if(list == null) {
            list = ArrayList()
            list!!.add(functionDeclarator)
        } else {
            sw = true
            list!!.forEach { data -> run {
                if(data.parameter != null && functionDeclarator.parameter != null && (data.parameter!!.size == functionDeclarator.parameter!!.size))
                if(functionDeclarator.name is QualifiedName) {
                sw = sw xor solveQualifiedName(data, (functionDeclarator.name as QualifiedName))
                } else if(functionDeclarator.name is Name) {
                    sw = sw xor solveName(data, (functionDeclarator.name as Name))
                }
            } }
        }
        if(!sw) {
            list!!.add(functionDeclarator)
        }
    }
    private fun solveName(functionDeclarator: FunctionDeclarator, name: Name): Boolean {
        return if(functionDeclarator.name is Name) {
            name.equals(functionDeclarator.name)
        } else {
            false
        }
    }

    private fun solveQualifiedName(function: FunctionDeclarator, qualifiedName: QualifiedName): Boolean {
        return if(function.name is QualifiedName) {
            val test1 = qualifiedName.lastName!!.equals((function.name as QualifiedName).lastName)
            val test2 =(qualifiedName.qualifier?.get(0) as Name).name.equals(((function.name as QualifiedName).qualifier?.get(0) as Name).name)
            test1 && test2
        } else {
            false
        }
    }

}