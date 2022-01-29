package com.stannis.function

import com.stannis.dataModel.statementTypes.*

object FunctionDefinitionRegistry {

    var list: ArrayList<FunctionDefinition>? = null

    fun addToList(data: FunctionDefinition) {
        if(list == null) {
            list = ArrayList()
        }
        val newFunctionDefinition = FunctionDefinition(data.declaratorSpecifier, data.declarator, null)
        removeUnwantedTypes(data, newFunctionDefinition)
        list!!.add(newFunctionDefinition)
    }

    private fun removeUnwantedTypes(data: FunctionDefinition, newFunctionDefinition: FunctionDefinition) {
        val compoundStatement = CompoundStatement(null)
        if(data.body is CompoundStatement) {
            (data.body as CompoundStatement).statements!!.iterator().forEach { statement -> run {
                when (statement) {
                    is SimpleDeclaration -> {
                        compoundStatement.addStatement(statement)
                        println(statement)
                    }
                    is DeclarationStatement -> {
                        statement.declarations!!.iterator().forEach { declaration -> run {
                            compoundStatement.addStatement(declaration)
                        } }
                    }
                    is FunctionCalls -> {
                        compoundStatement.addStatement(statement)
                    }
                }
            } }
        }
        newFunctionDefinition.body = compoundStatement
    }
}