package com.stannis.function

import com.stannis.dataModel.statementTypes.*
import com.stannis.parser.metrics.Metrics

object FunctionDefinitionRegistry {

    var listFromTranslationUnit = ArrayList<FunctionDefinition>()

    var listOfDefinitionOOP = ArrayList<FunctionDefinition>()

    fun addToList(element: FunctionDefinition) {
        Metrics.solveFunctionDefinition(element, false)
        if ((element.declarator?.get(0) as FunctionDeclarator).name !is QualifiedName) {
            if (element.body!![0] is CompoundStatement) {
                element.body!!.removeAt(0)
            }
            listFromTranslationUnit.add(element)
        } else {
            checkFunctionParent(element)
        }
    }

    private fun checkFunctionParent(element: FunctionDefinition) {
        if(!NameSpaceRegistry.checkInNameSpace(element)){
            CompositeTypeRegistry.checkInClass(element)
        }
    }

}
