package com.stannis.function

import com.stannis.dataModel.statementTypes.FunctionDeclarator
import com.stannis.dataModel.statementTypes.Name
import com.stannis.dataModel.statementTypes.ParameterDeclaration
import com.stannis.dataModel.statementTypes.QualifiedName

object FunctionDeclaratorRegistry {

    var list: ArrayList<FunctionDeclarator>? = null

    fun clearList(){
        list = null
    }

    var sw = true
    fun addToList(functionDeclarator: FunctionDeclarator) {
        if(list == null) { // here
            list = ArrayList()
            list!!.add(functionDeclarator)
        } else {
            sw = false
            var sw1: Boolean
            var sw2: Boolean
            list!!.forEach { data -> run {
                    if (functionDeclarator.name is QualifiedName) {
                        sw1 = solveQualifiedName(data, (functionDeclarator.name as QualifiedName), functionDeclarator)// check for interface
                        sw = sw || sw1
                    } else if (functionDeclarator.name is Name) {
                        sw2 = solveName(data, (functionDeclarator.name as Name), functionDeclarator)
                        sw = sw || sw2
                    }
            } }
        }
        if(!sw) {
            list!!.add(functionDeclarator)
            println()
        }
    }
    private fun solveName(
        functionDeclarator: FunctionDeclarator,
        name: Name,
        functionDeclarator1: FunctionDeclarator
    ): Boolean {
        return if(functionDeclarator.name is Name) {
            checkParameters(functionDeclarator, name, functionDeclarator1)
        } else {
            false
        }
    }

    private fun checkParameters(
        functionDeclarator: FunctionDeclarator,
        name: Name,
        functionDeclarator1: FunctionDeclarator
    ): Boolean {
        return if (name == functionDeclarator.name && functionDeclarator.parameter != null) {
            var switch1 = 0
            if (functionDeclarator1.parameter != null) {
                functionDeclarator.parameter!!.iterator().forEachRemaining { param ->
                    run {
                        functionDeclarator1.parameter!!.iterator().forEachRemaining { param1 ->
                            run {
                                if ((param1 as ParameterDeclaration).declarator == (param as ParameterDeclaration).declarator) {
                                    switch1 += 1
                                }
                            }
                        }
                    }
                }
            }
            switch1 != functionDeclarator1.parameter!!.size
        } else if(functionDeclarator.name is QualifiedName) {
            return checkParameters(functionDeclarator1, (functionDeclarator.name as QualifiedName).lastName as Name, functionDeclarator)
        } else {
            name == functionDeclarator.name
        }
    }

    private fun solveQualifiedName(
        function: FunctionDeclarator,
        qualifiedName: QualifiedName,
        functionDeclarator: FunctionDeclarator
    ): Boolean {
        return when (function.name) {
            is QualifiedName -> {
                val test1 = qualifiedName.lastName!! == (function.name as QualifiedName).lastName
                val test2 =(qualifiedName.qualifier?.get(0) as Name).name.equals(((function.name as QualifiedName).qualifier?.get(0) as Name).name)
                !(test1 && test2)
            }
            is Name -> {
                if(!checkParameters(functionDeclarator, function.name as Name, function)) {
                    qualifiedName.lastName!! != function.name
                } else {
                    true
                }
            }
            else -> {
                true
            }
        }
    }

}