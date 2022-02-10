package com.stannis.function

import com.stannis.dataModel.statementTypes.FunctionDeclarator
import com.stannis.dataModel.statementTypes.Name
import com.stannis.dataModel.statementTypes.QualifiedName

object FunctionDeclaratorRegistry {

    var list: ArrayList<FunctionDeclarator>? = null

    fun clearList(){
        list = null
    }

    var sw: Boolean = false

    fun addToList(functionDeclarator: FunctionDeclarator) { // declarari de functii, fara body. metode fara implementare, interfete etc.
        if(list == null) { // here
            list = ArrayList()
        } else {
            if(functionDeclarator.name is Name) {
                sw = solveName(functionDeclarator)
            } else if(functionDeclarator.name is QualifiedName) {
                sw = solveQualifiedName(functionDeclarator)
            }
        }
        if(!sw) {
            list!!.add(functionDeclarator)
            println()
        }
    }

    private fun solveQualifiedName(functionDeclarator: FunctionDeclarator): Boolean {
        var sw1 = false
        list!!.iterator().forEachRemaining { element -> run {
            if(element.name is QualifiedName) {
                if((element.name as QualifiedName).equals(functionDeclarator.name)) {
                    sw1 = checkParameters(element, functionDeclarator)
                }
            } else if(element.name is Name) {
                if((functionDeclarator.name as QualifiedName).lastName!!.equals(element.name)) {
                    sw1 = true
                }
            }
        } }
        return sw1
    }

    private fun checkParameters(elementFromList: FunctionDeclarator, functionDeclarator: FunctionDeclarator): Boolean {
        var number = 0
        elementFromList.parameter!!.iterator().forEachRemaining { element -> run {
            functionDeclarator.parameter!!.iterator().forEachRemaining { parameter -> run {
                if(element.equals(parameter)) {
                    number += 1
                }
            } }
        } }
        if(number.equals(functionDeclarator.parameter!!.size)) {
            return true
        }
        return false
    }

    private fun solveName(functionDeclarator: FunctionDeclarator): Boolean {
        var sw1 = false
        list!!.iterator().forEachRemaining { element -> run {
            if(element.name is Name) {
                if((element.name as Name).equals(functionDeclarator.name)) {
                    sw1 = sw1 || checkParameters(element, functionDeclarator)
                }
            }
        } }
        return sw1
    }


}