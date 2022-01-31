package com.stannis.function

import com.stannis.dataModel.Statement
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
        removeUnwantedTypes(data, newFunctionDefinition)
        list!!.add(newFunctionDefinition)
        println()
    }

    private fun addStatement(statement: Statement, compoundStatement: CompoundStatement, data: FunctionDefinition) {
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
                if(data.body != null) {
                    compoundStatement.addStatement(
                        findDeclarationForFunctionCall(
                            data.body as CompoundStatement,
                            statement,
                            data
                        )
                    )
                }
            }
            is BinaryExpression -> {
//                        throw Exception()
            }
        }
    }

    private fun removeUnwantedTypes(data: FunctionDefinition, newFunctionDefinition: FunctionDefinition) {// prelucrare body
        val compoundStatement = CompoundStatement(null)
        if(data.body is CompoundStatement) {
            (data.body as CompoundStatement).statements!!.iterator().forEach { statement -> run {
                addStatement(statement, compoundStatement, data)
            } }
        }
        newFunctionDefinition.body = compoundStatement
    }

    private fun findDeclarationForFunctionCall(
        body: CompoundStatement,
        statement: FunctionCalls,
        data: FunctionDefinition
    ): FunctionCallWithDeclaration {
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
                SimpleDeclarationRegistry.list!!.iterator().forEachRemaining { declaration -> run {
                    declaration.declarators!!.iterator().forEachRemaining { declarator -> run {
                        if(declarator is Declarator) {
                            if(statements.name is FieldReference) {
                                if (declarator.name.equals(((statements.name as FieldReference).fieldOwner as IdExpression).expression.toString())) {
                                    val arrlist: ArrayList<Statement>? = ArrayList()
                                    arrlist!!.add(declarator)
                                    functionCallWithDeclaration.declaration =
                                        SimpleDeclaration(arrlist, declaration.declSpecifier)
                                }
                            } else if(statements.name is IdExpression) {
                                statements.arguments!!.iterator().forEachRemaining { argument -> run {
                                    if(argument is FunctionCalls) {
                                        addStatement(argument, body, data)
                                    }
                                } }
                                println()
                            }
                        }
                    } }
                } }
            }
        } }
        return functionCallWithDeclaration
    }
}