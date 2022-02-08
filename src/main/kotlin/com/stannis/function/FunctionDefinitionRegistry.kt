package com.stannis.function

import com.stannis.dataModel.statementTypes.*

object FunctionDefinitionRegistry {

    var list: ArrayList<FunctionDefinition>? = null

    fun clearList() {
        list = null
    }

    fun addToList(data: FunctionDefinition) {
        if (list == null) {
            list = ArrayList()
        }
        val newFunctionDefinition = FunctionDefinition(data.declaratorSpecifier, data.declarator, null)
//        removeUnwantedTypes(data, newFunctionDefinition)
        list!!.add(newFunctionDefinition)
        println()
    }

    private fun removeUnwantedTypes(oldFunctionDefinition: FunctionDefinition, newFunctionDefinition: FunctionDefinition) { // save only functionCalls, Declaration
        (oldFunctionDefinition.body as CompoundStatement).statements!!.iterator().forEachRemaining { statement -> run  {
            when (statement) {
                is FunctionCalls -> {
                    SimpleDeclarationRegistry.list!!.iterator().forEachRemaining { simpleDeclaration -> run {
                        simpleDeclaration.declaration.declarators!!.iterator().forEachRemaining { declaration -> run {
                            if(declaration is Declarator) {
                                if((statement.name as IdExpression).expression.toString().equals(declaration.name)) {

                                } else {
                                    //internal method
                                }
                            }
                        }}
                    }}
                }
                is Declarator -> {

                }
                else -> {
                    throw Exception()
                }
            }
        }}
    }

}