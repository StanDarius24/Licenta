package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.DeclarationWithParent
import com.stannis.dataModel.complexStatementTypes.FunctionCallWithDeclaration
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
        TranslationUnitRegistry.setFunctionCalls(list)
        println()
    }

    private fun removeUnwantedTypes(oldFunctionDefinition: FunctionDefinition, newFunctionDefinition: FunctionDefinition) { // save only functionCalls, Declaration
        oldFunctionDefinition.body!!.iterator().forEachRemaining { statements -> run {
            if(statements is CompoundStatement) {
                removeIfBodyCompoundStatement(statements, newFunctionDefinition)
            }
        } }
    }

    private fun removeIfBodyCompoundStatement(statements: CompoundStatement, newFunctionDefinition: FunctionDefinition) {
        statements.statements!!.iterator().forEachRemaining { statement -> run  {
            when (statement) {
                is FunctionCalls -> {
                        resolveWhenStatementIsFunctionCall(statement, newFunctionDefinition)
                }
            }
        }}
    }

    private fun resolveWhenStatementIsFunctionCall(statement: FunctionCalls, newFunctionDefinition: FunctionDefinition) {
        if(!checkInternDeclaration(statement, newFunctionDefinition)) {
            checkGlobalDeclaration(statement, newFunctionDefinition)
        }
    }

    private fun checkGlobalDeclaration(statement: FunctionCalls, newFunctionDefinition: FunctionDefinition) {
        SimpleDeclarationRegistry.globalDeclaration!!.iterator().forEachRemaining { declaration -> run {
            verifyIfFunctionCallDeclaration(statement, declaration, newFunctionDefinition, true)
        } }
    }

    private fun checkInternDeclaration(statement: FunctionCalls, newFunctionDefinition: FunctionDefinition): Boolean {
        var bool1 = false
        SimpleDeclarationRegistry.internDeclaration!!.iterator().forEachRemaining { declaration -> run {
            bool1 = verifyIfFunctionCallDeclaration(statement, declaration, newFunctionDefinition, false)
        } }
        return bool1
    }

    private fun verifyIfFunctionCallDeclaration(statement: FunctionCalls, declaration: DeclarationWithParent, newFunctionDefinition: FunctionDefinition, boolean: Boolean): Boolean {
        var bool =false
        declaration.declaration.declarators!!.iterator().forEachRemaining { declarator -> run {
            if((declarator as Declarator).name.equals(getStatementName(statement, boolean))) {
                val functionCallWithDeclaration = FunctionCallWithDeclaration(statement, declarator)
                newFunctionDefinition.addToBody(functionCallWithDeclaration)
                bool = true
            }
        }
        }
        return bool
    }

    private fun getStatementName(statement: FunctionCalls, boolean: Boolean): String? {
        if(statement.name is FieldReference) {
            if((statement.name as FieldReference).fieldOwner is IdExpression) {
                if(((statement.name as FieldReference).fieldOwner as IdExpression).expression is Name) {
                    return (((statement.name as FieldReference).fieldOwner as IdExpression).expression as Name).name
                } else if(((statement.name as FieldReference).fieldOwner as IdExpression).expression is QualifiedName && boolean) {
                    if((((statement.name as FieldReference).fieldOwner as IdExpression).expression as QualifiedName).lastName is Name) {
                        return ((((statement.name as FieldReference).fieldOwner as IdExpression).expression as QualifiedName).lastName as Name).name
                    }
                }
            }
        } else if(statement.name is IdExpression) {
            if((statement.name as IdExpression).expression is Name) {
                return ((statement.name as IdExpression).expression as Name).name
            }
        }
        return null
    }


}