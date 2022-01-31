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
        println()
    }

    private fun removeUnwantedTypes(data: FunctionDefinition, newFunctionDefinition: FunctionDefinition) {
        val compoundStatement = CompoundStatement(null)
        if(data.body is CompoundStatement) {
            (data.body as CompoundStatement).statements!!.iterator().forEach { statement -> run {
                when (statement) {
                    is SimpleDeclaration -> {
                        compoundStatement.addStatement(statement)
                    }
                    is DeclarationStatement -> {
                        statement.declarations!!.iterator().forEach { declaration -> run {
                            compoundStatement.addStatement(declaration)
                        } }
                    }
                    is FunctionCalls -> {
                        compoundStatement.addStatement(findDeclarationForFunctionCall(data.body as CompoundStatement, statement))
                    }
                    is BinaryExpression -> {

                    }
                }
            } }
        }
        newFunctionDefinition.body = compoundStatement
    }

    private fun findDeclarationForFunctionCall(body: CompoundStatement, statement: FunctionCalls): FunctionCallWithDeclaration {
        val functionCallWithDeclaration = FunctionCallWithDeclaration(statement, null)
        body.statements!!.iterator().forEachRemaining { statements -> run {
            if(statements is DeclarationStatement) {
                statements.declarations!!.iterator().forEach { declaration -> run {
                    if(declaration is SimpleDeclaration) {
                        declaration.declarators!!.forEach { decl -> run {
                            if(decl is Declarator) {
                                if(decl.name == ((statement.name as FieldReference).fieldOwner as IdExpression).expression) {
                                    functionCallWithDeclaration.declaration = decl
                                }
                            }
                        } }
                    }
                } }
            } else if(statements is FunctionCalls) {
                println() // save all declarations get them and check what types function call is.
            }
        } }
        if(functionCallWithDeclaration.declaration == null) { // global declaration
            println()
        }
        return functionCallWithDeclaration
    }
}